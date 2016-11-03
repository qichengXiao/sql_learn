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
                    result += '<a href=\"'+"/sun-government/news/info/"+data.lists[i].channel+"/"+data.lists[i].id+'\" class="item"><p class="policy-title"><span class="title-circle">·</span>'+data.lists[i].title+'</p><p class="policy-message"><span>'+data.lists[i].releaseTime+'</span>&nbsp;&nbsp;来源:<span>'+data.lists[i].source+'</span></p><span class="select-button">></span></a>';
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
$('.inner').height($(window).height() - $('.foot-navbar').height());
/*$('.item').on('click', function() {
    $(this).addClass('active-catalog');
});*/
$('.item').on('touchstart', function() {
    console.log('a');
    $(this).addClass('active-catalog');
});
$('.item').on('touchend', function() {
    console.log('a');
    $(this).removeClass('active-catalog');
});