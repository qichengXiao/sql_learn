
var localObj = window.location;

var contextPath = localObj.pathname.split("/")[1];
$("#nextPageBtn").bind('click',function(){
	var title=$("#search_area").val();
	var channel=$("#channel").val();
	//下一页的页码
	var pageid=$("#pageid").val();
	var url="";
	if(title=='')
		url=channel+"/"+pageid;
	else
		url="/search/news/ajax";
	$.ajax({
        type: 'post',
        url: url,
        data:{'title':title,'channel':channel,'pageid':pageid},
        dataType: 'json',
        success: function(data){
            var result = '';
            if(data.lists.length<15||data.lists.length==0){
            	$("#nextPageBtn").hide();
            	$("#finalPageMassage").show();
            }
            else{
	            for(var i = 0; i < data.lists.length; i++){
	                result += '<li><a href="/'+"/news/info/"+data.lists[i].channel+"/"+data.lists[i].id+'\" class="list_item"><p class="policy_title">'+data.lists[i].title+'</p><i class="icon_arrow"></i></a></li>';
	            }
	            $("#pageid").val(data.pageid);
	            $('.news_list').append(result);
            }
        },
        error: function(xhr, type){
        	alert('网络好像出了点问题!');
        }
    });
});

$("#search_area").bind('input propertychange',function(){
	var title=$("#search_area").val();
	var channel=$("#channel").val();
	$("#nextPageBtn").hide();
	$("#finalPageMassage").hide();
	$("#newsList").empty();
	$.ajax({
        type: 'post',
        url: "/search/news",
        data:{'title':title,'channel':channel},
        dataType: 'json',
        success: function(data){
            var result = '';
            if(data.lists.length==0){
            	$("#finalPageMassage").show();
            	return;
            }
            for(var i = 0; i < data.lists.length; i++){
            	 result += '<li><a href=\"'+"/news/info/"+data.lists[i].channel+"/"+data.lists[i].id+'\" class="list_item"><p class="policy_title">'+data.lists[i].title+'</p><i class="icon_arrow"></i></a></li>';  
            	 }
	        $("#pageid").val(data.pageid);
	        $('.news_list').append(result);
	        if(data.lists.length<15) 
	        	$("#nextPageBtn").hide();
	        else
	            $("#nextPageBtn").show();
        },
        error: function(xhr, type){
        	alert('网络好像出了点问题!');
        }
    });
});