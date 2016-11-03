$("#nextPageBtn").bind('click',function(){
            var pageid=$("#pageid").val();
            var action=$("#action").val();
            $.ajax({
                type: 'GET',
                url: action+"/"+pageid,
                dataType: 'json',
                success: function(data){
                    var result = '';
                    if(data.lists.lenght<20||data.lists.length==0){
                    	$("#nextPageBtn").hide();
                    	$("#finalPageMassage").show();
                    	return;
                    }
                    if(action=="remark"){
	                    for(var i = 0; i < data.lists.length; i++){
	                    		result+='<li>'+data.lists[i].bu_title+'</li>';
	                    }
                    }
                    else if(action=="pendingReview"){
                    	 for(var i = 0; i < data.lists.length; i++){
                    		 var reviewRecord=data.lists[i];
		                    	result+='<li class="list_wrap">'
		            			+'<p class="list_title">'+reviewRecord.zh_title+'</p>'
		            			+'<p class="list_title_type">技术领域：'
		            				+'<span>${reviewRecord.tech_name}</span>'
		            			+'</p>'
		            			+'<ul class="list_info_area">'
		            				+'<li class="list_left_area">'
		            					+'<p>受理编号</p>'
		            					+'<p>'+reviewRecord.prp_no+'</p>'
		            				+'</li>'
		            				+'<li class="list_middle_area">'
		            					+'<p>评审类型</p>'
		            					+'<p>'+reviewRecord.atv_type+'</p>'
		            				+'</li>'
		            				+'<li class="list_right_area">'
		            					+'<p>评议状态</p>'
		            					+'<p>'+reviewRecord.status+'</p>'
		            				+'</li>'
		            			+'</ul>'
		            		+'</li>';
                    	 }
                    }
                    else if(action=="detail"){
                    	for(var i = 0; i < data.lists.length; i++){
                    		var projectDetail=data.lists[i];
                    		result+='<li class="details_item">'
                			+'<p class="detail_title">'
                				+projectDetail.zh_title
                				+'<span class="detail_title_num">'
                					+'[<span>'+projectDetail.prj_code+'</span>]'
                				+'</span>'
                				+'<span class="detail_title_mark">'+projectDetail.status+'</span>'
                			+'</p>'
                			+'<p class="detail_type">·&nbsp;业务类型:'
                				+'<span class="detail_type_text">'+projectDetail.grant_name+'</span>'
                			+'</p>'
                			+'<p class="detail_unit">·&nbsp;单位名称:'
                				+'<span class="detail_unit_text">'+projectDetail.org_name+'</span>'
                			+'</p>'
                			+'<table class="detail_info">'
                				+'<tr>'
                					+'<td>'
                						+'<p>立项年度</p>'
                						+'<p>'+projectDetail.stat_year+'</p>'
                					+'</td>'
                					+'<td>'
                						+'<p>立项金额</p>'
                						+'<p>'+projectDetail.award_amt+'</p>'
                					+'</td>'
                					+'<td>'
                						+'<p>合同截止日期</p>'
                						+'<p>'+projectDetail.end_date+'</p>'
                					+'</td>'
                					+'<td>'
                						+'<p>负责人登录名</p>'
                						+'<p>'+projectDetail.login_name+'</p>'
                					+'</td>'
                				+'</tr>'
                			+'</table>'
                		+'</li>';
                    	}
                    }
                    else if(action=="own"){
                    	for(var i = 0; i < data.lists.length; i++){
                    		var projectRecord=data.lists[i];
                    		result+='<li class="details_item">'
                			+'<p class="detail_title">'
                				+projectRecord.zh_title
                			+'</p>'
                			+'<p class="detail_type">·&nbsp;业务类型:'
                				+'<span class="detail_type_text">'+projectRecord.grant_name+'</span>'
                			+'</p>'
                			+'<table class="detail_info">'
                				+'<tr>'
                					+'<td>'
                						+'<p>年度/批次</p>'
                						+'<p>'+projectRecord.stat_year+'/'+projectRecord.batch+'</p>'
                					+'</td>'
                					+'<td>'
                						+'<p>提交时间</p>'
                						+'<p>'+projectRecord.submit_date+'</p>'
                					+'</td>'
                					+'<td>'
                						+'<p>工作进度</p>'
                						+'<p>'+projectRecord.status+'</p>'
                					+'</td>'
                					+'<td>'
                						+'<p>审核意见</p>'
                						+'<p>查看</p>'
                					+'</td>'
                				+'</tr>'
                			+'</table>'
                		+'</li>';
                    	}
                    }
                    else if(action=="invitation"){
                    	for(var i = 0; i < data.lists.length; i++){
                    		var invitation=data.lists[i];
                    		result+='<li class="list_wrap invite_wrap">'
                			+'<p class="list_title">'+invitation.atv_type+'——'+invitation.group_name+'</p>'
                			+'<ul class="list_info_area">'
                				+'<li class="list_left_area">'
                					+'<p>评审时间</p>'
                					+'<p>'+invitation.review_date+'</p>'
                				+'</li>'
                				+'<li class="list_middle_area">'
                					+'<p>评审项目数</p>'
                					+'<p>'+invitation.review_num+'</p>'
                				+'</li>'
                				+'<li class="list_right_area">'
                					+'<p>确认结果</p>'
                					+'<p>'+invitation.ev_pass_result+'</p>'
                				+'</li>'
                			+'</ul>'
                		+'</li>';
                    	}
                    }
                    $("#pageid").val(data.pageid);
                    $('#lists').append(result);
                },
                error: function(xhr, type){
                	alert('网络好像出了点问题!');
                }
            });
});

$(document).ready(function () {
	var count=$("#recordCount").val();
	if(count==null||count<20)
		$("#nextPageBtn").hide();
});