package com.helper.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.classifycandidatepro.model.AnswerVO;
import com.classifycandidatepro.model.Question;



public class HelperUtil {

	public ArrayList<Question> convertAnswerVOMapToQuestionVOList(
			Map<Integer, AnswerVO> answerVOMap) {

		ArrayList<Question> questionVOs = new ArrayList<Question>();

		Iterator<Entry<Integer, AnswerVO>> iterator = answerVOMap.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer, AnswerVO> entry = (Map.Entry<Integer, AnswerVO>) iterator
					.next();

			AnswerVO answerVO = entry.getValue();

			Question questionVO = answerVO.getQuestion();

			questionVOs.add(questionVO);

		}

		return questionVOs;
	}

	public static int getRandomNumberInRange(int min, int max) {

		List<Integer> indexes = new ArrayList<Integer>();

		for (int i = 0; i < max; i++) {
			indexes.add(i);
		}

		Collections.shuffle(indexes);

		return indexes.get(0);

	}

}
