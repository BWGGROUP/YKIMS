/**
 * Created by shbs-tp001 on 15-9-10.
 */
var optactive=0;
var menuactive=0;
$(function () {

    click_lisetn();
});

function click_lisetn(){
    $.jqPaginator('#pagination1', {
        totalPages: 100,
        visiblePages: 5,
        currentPage: 1,
        onPageChange: function (num, type) {
            //$('#p1').text(type + '：' + num);
        }
    });

    $('.btn-back').click(function(){
        location.href='javascript:history.go(-1);'
    })
}