Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
Ext.tip.QuickTipManager.init();



var webColumns=[
         			
         			{
         				header : 'Rating Type',
         				dataIndex : 'ratingType',
         				sortable:true,
         				width    :100
         			},
					{
         				header : 'Company Name',
         				dataIndex : 'companyName',
         				sortable:true,
         				width    :230
         			},
					{
         				header : 'Service Type',
         				dataIndex : 'servicetype',
         				sortable:true,
         				width    :230
         			},
					{
         				header : 'Rating',
         				dataIndex : 'rating',
         				sortable:true,
         				width    :230
         			}
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
	
	console.log('url');
	console.log(users_end_point_with_appname_logintype_customer);
	
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
		 			{name:'rating', mapping:'rating',type:'double'},
					{name:'ratingType', mapping:'ratingType',type:'string'},
					{name:'companyName', mapping:'companyName',type:'string'},
					{name:'servicetype', mapping:'servicetype',type:'string'}	
				 ]
		
	});

	webStore = Ext.create('Ext.data.Store', {
		id : 'webSiteStoreId',
		name : 'webSiteStoreName',
		model : 'webModel',
		proxy : {
			type : 'ajax',
			autoLoad: {start: 0, limit: 15},
			url :contextPath+'/viewTotalRatingAll',
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
		title:'View Ratings',
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
            displayMsg: 'Displaying Rating {0} - {1} of {2}',
            emptyMsg: "No Ratings to display",
            inputItemWidth: 35
     })
	});

});
	
	
	
