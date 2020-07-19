Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
Ext.tip.QuickTipManager.init();



var webColumns=[
         			{
         				header : 'Question Desc',
         				dataIndex : 'questionDesc',
         				sortable:true,
         				width:100,
         				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
         					metadata.tdAttr = 'data-qtip="' + value + '"';
         					return value;
                		}
         			},
         			{
         				header : 'Answer1',
         				dataIndex : 'answer1',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Answer2',
         				dataIndex : 'answer2',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Answer3',
         				dataIndex : 'answer3',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Answer4',
         				dataIndex : 'answer4',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Rating1',
         				dataIndex : 'rating1',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Rating2',
         				dataIndex : 'rating2',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Rating3',
         				dataIndex : 'rating3',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Rating4',
         				dataIndex : 'rating4',
         				sortable:true,
         				width:100
         			},
         			];

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
	
	
Ext.define('Ext.data.proxy.Cors', {
	    
	    extend: 'Ext.data.proxy.Ajax',
	    
	    uses: ['Ext.data.Cors'],
	    
	    alias: 'proxy.cors',
	    
	    alternateClassName: ['Ext.data.CorsProxy'],
	    
	    doRequest: function(operation, callback, scope) {
	        var writer  = this.getWriter(),
	            request = this.buildRequest(operation, callback, scope);
	            
	        if (operation.allowWrite()) {
	            request = writer.write(request);
	        }
	        
	        Ext.apply(request, {
	            headers       : this.headers,
	            timeout       : this.timeout,
	            scope         : this,
	            callback      : this.createRequestCallback(request, operation, callback, scope),
	            method        : this.getMethod(request),
	            disableCaching: false // explicitly set it to false, ServerProxy handles caching
	        });
	        
	        Ext.Cors.request(request);
	        
	        return request;
	    }


	});

	
	Ext.define('webModel',{
		extend : 'Ext.data.Model',
		requires: ['Ext.data.proxy.Cors'],
		fields : [ 
		          	{name:'questionDesc', mapping:'questionDesc',type:'string'},
					{name:'answer1', mapping:'answer1',type:'string'},
					{name:'answer2', mapping:'answer2',type:'string'},
					{name:'answer3', mapping:'answer3',type:'string'},
					{name:'answer4', mapping:'answer4',type:'string'},
					{name:'rating1', mapping:'rating1',type:'int'},
					{name:'rating2', mapping:'rating2',type:'int'},
					{name:'rating3', mapping:'rating3',type:'int'},
					{name:'rating4', mapping:'rating4',type:'int'},
					{name:'questiontype', mapping:'questiontype',type:'string'}
				 ]
		
	});

	webStore = Ext.create('Ext.data.Store', {
		id : 'webSiteStoreId',
		name : 'webSiteStoreName',
		model : 'webModel',
		proxy : {
			type : 'ajax',
			autoLoad: {start: 0, limit: 15},
			url :view_questions_endpoints,
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
		title:'View Questions List',
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
            displayMsg: 'Displaying Questions {0} - {1} of {2}',
            emptyMsg: "No Questions to display",
            inputItemWidth: 35
     })
	});

});
	
	
	
