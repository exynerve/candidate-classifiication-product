package com.classifycandidatepro.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.classifycandidatepro.main.repository.ClassifyCandidateRepository;
import com.classifycandidatepro.main.repository.HistoryProfileUserSessionRepository;
import com.classifycandidatepro.main.repository.KNNDistanceComputeVectorRepository;
import com.classifycandidatepro.main.repository.QuestionRepository;
import com.classifycandidatepro.model.AnswerVO;
import com.classifycandidatepro.model.ClassifyInfo;
import com.classifycandidatepro.model.ClassifyUserCompute;
import com.classifycandidatepro.model.DataSet;
import com.classifycandidatepro.model.HistoryProfileUserSession;
import com.classifycandidatepro.model.KNNDistanceComputeVector;
import com.classifycandidatepro.model.Question;
import com.classifycandidatepro.response.StatusInfo;
import com.classifycandidatepro.storageobjects.CompanyInformationVO;
import com.model.messages.Messages;

@Service
public class ClassifyCandidateService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private ClassifyCandidateRepository classifyCandidateRepository;
	
	@Autowired
	private HistoryProfileUserSessionRepository historyProfileUserSessionRepository;

	@Autowired
	private KNNDistanceComputeVectorRepository kNNDistanceComputeVectorRepository;
	
	
	public List<AnswerVO> retriveAllGeneralQuestionsForUser(String userId) {
		List<AnswerVO> questionVOList = new ArrayList<AnswerVO>();
		try {

			List<Question> questionVOs = questionRepository.findAll();

			int pageNumberGlobal = 1;

			for (Question questionVO : questionVOs) {
				questionVO.setQuesId(pageNumberGlobal);
				AnswerVO answerVO = populateAnswerVO(userId, pageNumberGlobal, questionVO);
				questionVOList.add(answerVO);

				pageNumberGlobal = pageNumberGlobal + 1;
			}

		} catch (Exception e) {
			return questionVOList;
		}
		return questionVOList;
	}

	private AnswerVO populateAnswerVO(String patName, int pageNumberGlobal, Question questionVO) {
		AnswerVO answerVO = new AnswerVO();
		answerVO.setPageNumberGlobal(pageNumberGlobal);
		answerVO.setPatName(patName);
		questionVO.setPageNumber(pageNumberGlobal);
		answerVO.setQuestion(questionVO);
		return answerVO;
	}

	public ClassifyInfo processClassifyAndFind(ArrayList<Question> questionObj, String userId, String string,
			String sessionId) {

		ClassifyInfo classifyInfo = null;
		try {

			classifyInfo = new ClassifyInfo();

			Map<String, Double> hashMap = new HashMap<String, Double>();

			for (Question questionVO : questionObj) {

				String testName = questionVO.getQuestiontype();

				double tempRating = computeMarkForQuestion(questionVO);

				if (hashMap.containsKey(testName)) {

					double previousRating = hashMap.get(testName);
					double currentRating = previousRating + tempRating;
					hashMap.put(testName, currentRating);
				} else {
					hashMap.put(testName, tempRating);
				}

			}

			// Save teh Hashmap Information
			if (null == hashMap || hashMap.isEmpty()) {
				classifyInfo.setStatus(false);
				classifyInfo.setErrMsg("Could not Determine Likeness. Please try Again");
				return classifyInfo;
			}

			// Remove the Previous Likeness Information of the user and feed
			// latest
			List<ClassifyUserCompute> classifyUserCompute = new ArrayList<ClassifyUserCompute>();

			Iterator it = hashMap.entrySet().iterator();

			if (it != null) {

				while (it.hasNext()) {

					Map.Entry pair = (Map.Entry) it.next();

					if (pair != null) {

						ClassifyUserCompute classifyUserCompute1 = new ClassifyUserCompute();

						classifyUserCompute1.setFeatureType((String) pair.getKey());
						classifyUserCompute1.setScore((Double) pair.getValue());

						classifyUserCompute1.setUserId(userId);
						classifyUserCompute1.setSessionId(sessionId);

						classifyUserCompute.add(classifyUserCompute1);

					}
				}
			}

			//
			StatusInfo statusInfo = classifyCandidateRepository.deleteFromClassifyUserCompute(userId, sessionId);

			if (!statusInfo.isStatus()) {
				classifyInfo.setStatus(false);
				classifyInfo.setErrMsg("Could not remove the Old Classifcation Information for User");
				return classifyInfo;
			}

			// Save the Nutrition User Compute Information
			StatusInfo statusInfo2 = classifyCandidateRepository.insertBatchClassifyUserInfo(classifyUserCompute);

			if (!statusInfo2.isStatus()) {
				classifyInfo.setStatus(false);
				classifyInfo.setErrMsg("Could not Save Classify Information for User");
				return classifyInfo;
			}

			// Find the Class Label
			int totalQuestions = questionObj.size();

			int marks = totalQuestions * 4;

			List<Integer> totalMarksDistinct = classifyCandidateRepository.retriveDistinctMarksFromTestData();

			Map<Double, Integer> marksMap = new HashMap<Double, Integer>();

			List<Double> distanceList = new ArrayList<Double>();
			for (Integer tempMarks : totalMarksDistinct) {
				double distance = Math.abs(marks - tempMarks);
				marksMap.put(distance, tempMarks);
				distanceList.add(distance);
			}

			double minDistance = Collections.min(distanceList);

			int marksForDataSet = marksMap.get(minDistance);

			List<KNNDistanceComputeVector> knnComputeVectorList = new ArrayList<KNNDistanceComputeVector>();

			//
			List<DataSet> dataSets = classifyCandidateRepository.retriveDataSetsForMarks(marksForDataSet);

			if (dataSets != null && !dataSets.isEmpty()) {

				for (DataSet datasetTemp : dataSets) {

					double amptitude = datasetTemp.getAptitude();
					double general = datasetTemp.getGeneral();
					double technical = datasetTemp.getTechnical();

					double distanceApti = 0;
					double distanceGeneral = 0;
					double distanceTechnical = 0;

					for (ClassifyUserCompute classifierCompute : classifyUserCompute) {

						if (classifierCompute.getFeatureType().equals(Messages.QuestionType.APTITUDE_FEATURETYPE)) {

							double userScoreApti = classifierCompute.getScore();

							distanceApti = Math.abs(userScoreApti - amptitude);
						} else if (classifierCompute.getFeatureType()
								.equals(Messages.QuestionType.GENERAL_FEATURETYPE)) {

							double userScoregeneral = classifierCompute.getScore();

							distanceGeneral = Math.abs(userScoregeneral - general);

						} else {

							double userScoreTechnical = classifierCompute.getScore();

							distanceTechnical = Math.abs(userScoreTechnical - technical);

						}
					} // End of For Loop for Questions

					double distance = Math.sqrt(
							Math.pow(distanceTechnical, 2) + Math.pow(distanceGeneral, 2) + Math.pow(distanceApti, 2));

					KNNDistanceComputeVector knnDistanceComputeVector = new KNNDistanceComputeVector();

					knnDistanceComputeVector.setClusterNo(datasetTemp.getClusterNo());
					knnDistanceComputeVector.setDistance(distance);
					knnDistanceComputeVector.setUserId(userId);
					knnDistanceComputeVector.setSessionId(sessionId);

					knnComputeVectorList.add(knnDistanceComputeVector);
				}

				// Savve the KNN Classification Algorithm

				statusInfo = classifyCandidateRepository.deleteKNNDistanceMatrixForSessionIdAndUserId(sessionId,
						userId);

				if (!statusInfo.isStatus()) {
					classifyInfo.setStatus(false);
					classifyInfo.setErrMsg("Could not Remove Old KNN Distance");
					return classifyInfo;
				}

				List<KNNDistanceComputeVector> knnComputeVectorList1 = kNNDistanceComputeVectorRepository.saveAll(knnComputeVectorList);

				if (null==knnComputeVectorList1) {
					classifyInfo.setStatus(false);
					classifyInfo.setErrMsg("Could not Save KNN Distance");
					return classifyInfo;
				}

				//
				List<KNNDistanceComputeVector> knnDistanceComputeVectorsSorted = classifyCandidateRepository
						.retriveKNNDistanceMatrixForUserIdAndSessionIdDistanceAsc(userId, sessionId); 

				if (null == knnDistanceComputeVectorsSorted
						|| (knnDistanceComputeVectorsSorted != null && knnDistanceComputeVectorsSorted.isEmpty())) {
					classifyInfo.setStatus(false);
					classifyInfo.setErrMsg("No KNN Distance Compute Vector Found");
					return classifyInfo;
				}

				int k = knnDistanceComputeVectorsSorted.size();

				int kThreshold = (int) k / 2;

				Map<Integer, Integer> clusteNoCountMap = new HashMap<Integer, Integer>();

				int temp = 1;
				for (KNNDistanceComputeVector knnDistanceComputeVector : knnDistanceComputeVectorsSorted) {

					if (temp > kThreshold) {
						break;
					}

					int clusterNo = knnDistanceComputeVector.getClusterNo();

					if (null == clusteNoCountMap.get(clusterNo)) {

						clusteNoCountMap.put(clusterNo, 1);

					} else {

						int countTemp = clusteNoCountMap.get(clusterNo);

						int newCount = countTemp + 1;

						clusteNoCountMap.put(clusterNo, newCount);
					}

					temp = temp + 1;
				} // End of For

				Set<Integer> clusterNos = clusteNoCountMap.keySet();

				List<Integer> counterNoList = new ArrayList<Integer>();
				for (Integer clusterNoTemp : clusterNos) {

					int count = clusteNoCountMap.get(clusterNoTemp);

					counterNoList.add(count);
				}

				// Now finding maximum K Neigbors

				int maxCount = Collections.max(counterNoList);

				List<Integer> bestClusterNosList = new ArrayList<>();

				for (Integer clusterNoTemp : clusterNos) {

					int count = clusteNoCountMap.get(clusterNoTemp);

					if (count == maxCount) {

						bestClusterNosList.add(clusterNoTemp);
					}

				}
				
				List<HistoryProfileUserSession> historyProfileUserSessions = new ArrayList<HistoryProfileUserSession>();

				List<CompanyInformationVO> companyInformationVOs = new ArrayList<CompanyInformationVO>();

				// Now get the Recommendations Engine for User and Store
				// the History Profile
				for (Integer bestClusterNo : bestClusterNosList) {

					HistoryProfileUserSession historyProfileUserSession = new HistoryProfileUserSession();

					historyProfileUserSession.setClusterNo(bestClusterNo);
					historyProfileUserSession.setSessionId(sessionId);
					historyProfileUserSession.setUserId(userId);

					if (bestClusterNo == 1) {
						historyProfileUserSession.setClusterName(Messages.ClusterLabel.CLUSTERLABEL1);

						List<CompanyInformationVO> companyInformationVOsTemp = classifyCandidateRepository
								.retriveCompanyInformationForClusterName(Messages.ClusterLabel.CLUSTERLABEL1);

						companyInformationVOs.addAll(companyInformationVOsTemp);

					} else if (bestClusterNo == 2) {
						historyProfileUserSession.setClusterName(Messages.ClusterLabel.CLUSTERLABEL2);
						
						List<CompanyInformationVO> companyInformationVOsTemp = classifyCandidateRepository
								.retriveCompanyInformationForClusterName(Messages.ClusterLabel.CLUSTERLABEL2);

						companyInformationVOs.addAll(companyInformationVOsTemp);
						
					} else {
						
						List<CompanyInformationVO> companyInformationVOsTemp = classifyCandidateRepository
								.retriveCompanyInformationForClusterName(Messages.ClusterLabel.CLUSTERLABEL3);

						companyInformationVOs.addAll(companyInformationVOsTemp);
						
						historyProfileUserSession.setClusterName(Messages.ClusterLabel.CLUSTERLABEL3);
					}
					
					historyProfileUserSessions.add(historyProfileUserSession);

				}
				
				//Now Save the History Profile
				List<HistoryProfileUserSession> historyProfileUserSessions1 = historyProfileUserSessionRepository.saveAll(historyProfileUserSessions);
				
				if(null==historyProfileUserSessions1){
					classifyInfo.setStatus(false);
					classifyInfo.setErrMsg("Could not save History Profile for User");
					return classifyInfo;
				}
				
				//
				classifyInfo.setModel(companyInformationVOs);
				classifyInfo.setStatus(true);
				
				
				
			}

		} catch (Exception e) {
			classifyInfo.setStatus(false);
			classifyInfo.setErrMsg("Could not Save Classify Information for User");
			return classifyInfo;
		}
		classifyInfo.setStatus(true);
		return classifyInfo;

	}

	private int computeMarkForQuestion(Question questionVO) {

		int answer = questionVO.getSelectedAnswer();
		if (answer <= 0) {
			return -1;
		} else {
			if (answer == 1) {
				return questionVO.getRating1();
			}
			if (answer == 2) {
				return questionVO.getRating2();
			}
			if (answer == 3) {
				return questionVO.getRating3();
			}
			if (answer == 4) {
				return questionVO.getRating4();
			}

		}

		return 0;

	}
}
