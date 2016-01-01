/*
	jQuery Document Ready
*/
$(function()
{
	/*
		We are using setTimeout so after defined millisecond
		page will be automatically unblocked
	*/	
	
	/*
		Default Page Block
	*/
	$('#default').click(function()
	{
		$.blockUI();
		setTimeout($.unblockUI, 2000);
	});
	
	/*
		adding custom block message
	*/	
	$('.user-name').click(function()
	{
		$.blockUI(
		{
			/*
				message displayed when blocking (use null for no message)
			*/
			message: '<img src="http://s13.postimg.org/80vkv0coz/image.gif" /> Please Wait Until Detailed Report Generates...'
		});
		
	});
	$('#submitbutton').click(function()
			 {
			
				$.blockUI(
				{
					
					/*
						message displayed when blocking (use null for no message)
					*/
					message: '<img src="http://s13.postimg.org/80vkv0coz/image.gif" /> Please Wait Until Report Generates...'
				});
				
			});
	
	$('#generatereport').click(function()
			 {
		
				
				$.blockUI(
				{
					/*
						message displayed when blocking (use null for no message)
					*/
					message: '<img src="http://s13.postimg.org/80vkv0coz/image.gif" /> Downloading Report to Xls...'
				});
				
				setTimeout($.unblockUI, 2000); 
			});
	
	
	/*
		adding element block with custom message and css
	*/
	
	$('#blockElement').click(function()
	{
		$.blockUI(
		{
			/*
				message displayed when blocking (use null for no message)
			*/
			message: '<h1>Processing...</h1>',
			/*
				styles for the message when blocking; if you wish to disable 
				these and use an external stylesheet then do this in your code: 
				$.blockUI.defaults.css = {}; 
			*/
			css: { border: '3px solid #a00' }
		});
	});
	
	/*
		un block blocked element
	*/
	$('#custom').click(function()
	{
		$.blockUI().unblock();
	});
	
	/*
		show login form in page block
	*/
	$('#loginButton').click(function()
	{
        $.blockUI(
		{
			/*
				giving DOM object as message will 
				show given DOM html as block message,
				In our case it will show login form
			*/
			message: $('#loginForm')
		});
 
        setTimeout($.unblockUI, 2000); 
    });
});