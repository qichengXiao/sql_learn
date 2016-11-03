$(document).ready(function() {
	$("#com-content").on("focus",function() {
		$("#com-content-full").show('400', function() {
			$('.com-textarea').focus();
			$('.com-textarea-back').on('click', function() {
				var test = $('.com-textarea').val();
				$('#com-content').val(test);
				$("#com-content-full").hide();
			});
		});
	});
});
$("#addComplaints").bind('click',function(){

	if($("#com-open").is(":checked")==true)
		$("#com-open").val("1");
	else
		$("#com-open").val("0");
	$("#submitBtn").click();
	//$("#addRecord-form").submit();
}); 
$(document).ready(function() {
	if($("#errorMsg").text()!="")
		alert($("#errorMsg").text());
	$("#errorMsg").text("");
});
$("#acceptBtn").bind("click",function(){
	$("#checked").val("true");
	$("#channel-form").submit();
});

	
