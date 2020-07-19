Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
Ext.tip.QuickTipManager.init();



var webColumns=[
         			{
         				header : 'User Name',
         				dataIndex : 'userName',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Phone No',
         				dataIndex : 'phoneNo',
         				sortable:true,
         				width    :100
         			},
					{
         				header : 'State',
         				dataIndex : 'state',
         				sortable:true,
         				width    :230
         			},
					{
         				header : 'Country',
         				dataIndex : 'country',
         				sortable:true,
         				width    :230
         			},
					{
         				header : 'City',
         				dataIndex : 'city',
         				sortable:true,
         				width    :230
         			},
					{
         				header : 'Email',
         				dataIndex : 'email',
         				sortable:true,
         				width    :230
         			},
					{
         				header : 'Service Type',
         				dataIndex : 'service_type',
         				sortable:true,
         				width    :230
         			},{
         				header : 'Company Name',
         				dataIndex : 'company_name',
         				sortable:true,
         				width    :230
         			},{
         				header : 'Description',
         				dataIndex : 'description',
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
					{name:'loginType', mapping:'loginType',type:'int'},
		          	{name:'userName', mapping:'user_name',type:'string'},
					{name:'phoneNo', mapping:'phone_no',type:'string'},
					{name:'state', mapping:'state',type:'string'},
					{name:'country', mapping:'country',type:'string'},
					{name:'city', mapping:'city',type:'string'},
					{name:'email', mapping:'email',type:'string'},
					{name:'age', mapping:'age',type:'int'},
					{name:'service_type', mapping:'service_type',type:'string'},
					{name:'company_name', mapping:'company_name',type:'string'},
					{name:'gender', mapping:'gender',type:'string'},
					{name:'description', mapping:'description',type:'string'},
					
				 ]
		
	});

	webStore = Ext.create('Ext.data.Store', {
		id : 'webSiteStoreId',
		name : 'webSiteStoreName',
		model : 'webModel',
		proxy : {
			type : 'ajax',
			autoLoad: {start: 0, limit: 15},
			url :providers_end_point_with_appname_logintype_customer,
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
		title:'View Users',
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
            displayMsg: 'Displaying Users {0} - {1} of {2}',
            emptyMsg: "No Users to display",
            inputItemWidth: 35
     })
	});

});
	
	
	
