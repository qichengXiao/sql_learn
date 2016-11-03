//下拉刷新
var dropload = $('.inner').dropload({
    domDown : {
        domClass   : 'dropload-down',
        domRefresh : '<div class="dropload-refresh">↑上拉加载更多</div>',
        domUpdate  : '<div class="dropload-update">↓释放加载</div>',
        domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
    },
    loadDownFn : function(me){
    	var channel=$("#channel").val();
    	//下一页的页码
    	var pageid=$("#pageid").val();
        $.ajax({
            type: 'GET',
            url: channel+"/"+pageid,
            dataType: 'json',
            success: function(data){
                var result = '';
                for(var i = 0; i < data.lists.length; i++){
                    result += '<a href=\"'+"/sun-government/news/info/"+data.lists[i].channel+"/"+data.lists[i].id+'\" class="item"><span class="title-circle">·</span><p class="policy-title">'+data.lists[i].title+'</p></a>';
                }
                // 为了测试，延迟1秒加载
               /* setTimeout(function(){
                    $('.lists').append(result);
                    me.resetload();
                },1000);*/
                $("#pageid").val(data.pageid);
                $('.lists').append(result);
                me.resetload();
            },
            error: function(xhr, type){
            	alert('网络好像出了点问题!');
                me.resetload();
            }
        });
    }
});