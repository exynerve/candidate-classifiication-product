Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
Ext.tip.QuickTipManager.init();



var webColumns=[
				{
					header : 'ID',
					dataIndex : 'id',
					sortable:true,
					width:100
				},
         			
         			{
         				header : 'Cluster No',
         				dataIndex : 'clusterNo',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Distance',
         				dataIndex : 'distance',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Session Id',
         				dataIndex : 'sessionId',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'User Id',
         				dataIndex : 'userId',
         				sortable:true,
         				width:100
         			}];

var hideConfirmationMsg;
var showConfirmationMsg;
/* Hide the Confirmation Message */
	hideConfirmationMsg = function() {
		var confMsgDiv = Ext.get('confirmationMessage');
		confMsgDiv.dom.innerHTML = "";
		confMsgDiv.dom.style.display = 'none';
	}
	/* Show Confirmation Message */
	showConfirmationMsg = function(msg) {
		var confMsgDiv = Ext.get('confirmationMessage');
		confMsgDiv.dom.innerHTML =  msg;
		confMsgDiv.dom.style.display = 'inline-block';		
	}
	var webSiteStore;
Ext.onReady(function () {

	var loadMask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading"});
	loadMask.show();
	
	


	
	Ext.define('webModel',{
		extend : 'Ext.data.Model',
		fields : [ 
		          	{name:'userId', mapping:'userId',type:'string'},
					{name:'sessionId', mapping:'sessionId',type:'string'},
					{name:'clusterNo', mapping:'clusterNo',type:'int'},
					{name:'distance', mapping:'distance',type:'double'},
					{name:'id', mapping:'id',type:'int'}
				]
		
	});

	webStore = Ext.create('Ext.data.Store', {
		id : 'webSiteStoreId',
		name : 'webSiteStoreName',
		model : 'webModel',
		proxy : {
			type : 'ajax',
			autoLoad: {start: 0, limit: 15},
			url :contextPath+'/viewKNNDistanceAllUser',
			extraParams:{
			},
			actionMethods:{
				read:'GET'
			},
			reader : {
				type :'json',
				root:'model',
				totalProperty: 'total'
					}
		},
		listeners:
		{
			'load':function(store, records){
						
				loadMask.hide();
			}
		},
		autoLoad : true
	});
	
	
	
	
	
	var webSiteTableGrid = Ext.create('Ext.grid.Panel', {
		title:'View KNN User Data',
		forceFit : true,
		id : 'webSiteGrid',
		store : webStore,
		columns : webColumns,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : Ext.getBody(),
		collapsible:true,
		overflowY:'auto',
		bbar: Ext.create('Ext.PagingToolbar', {
            store: webStore,
            displayInfo: true,
            displayMsg: 'Displaying KNN {0} - {1} of {2}',
            emptyMsg: "No KNN to display",
            inputItemWidth: 35
     })
	});

});
	
	
	
