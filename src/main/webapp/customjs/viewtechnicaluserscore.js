Ext.require('Ext.chart.*');
Ext.require('Ext.layout.container.Fit');
Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});



Ext.onReady(function () {
	
	Ext.define('technicalModel', {
		extend : 'Ext.data.Model',
		fields : [ 
		           {name:'score', mapping:'score',type:'double'},
		           {name:'iterationNo', mapping:'iterationNo',type:'int'}
				 ]
		});
	
	var techincalScore = Ext.create('Ext.data.Store', {
		id : 'techincalScoreId',
		name : 'techincalScoreName',
		model : 'technicalModel',
		proxy : {
			type : 'ajax',
			url :contextPath+'/viewTechnicalUserScoreGraph',
			actionMethods:{
				read:'GET'
			},
			reader : {
				type :'json',
				root:'model',
				totalProperty:'total'
			}
		},
		listeners:
		{
			'load':function(store, records){
			
				if(store.getCount()==0)
				{
					Ext.Msg.alert('status','Track History Could not be Plotted at this point of Time');
			
				}			
		}
		},
		autoLoad : true
	});
	techincalScore.load();
	
	Ext.create('Ext.chart.Chart', {
		   renderTo: 'graph',
		   width: 400,
		   height: 300,
		   store: techincalScore,
		   legend: {
			    position: 'right'
			},
		   axes: [
		          {
		              title: 'Score',
		              type: 'Numeric',
		              position: 'left',
		              fields: ['score']
		           
		          },
		          {
		              title: 'Number of Tests',
		              type: 'Numeric',
		              position: 'bottom',
		              fields: ['iterationNo']
		          }
		      ],
		      series: [
		               {
		                   type: 'line',
		                   xField: 'iterationNo',
		                   yField: 'score'
		               }
		           ]
		});
	

});    
           