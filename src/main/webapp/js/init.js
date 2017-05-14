/**
 * Created by nostalie on 17-5-13.
 */
$(document).ready(function () {
    initDepartment();
    initPosition();
});

function initDepartment() {
    $.ajax({
        type:"post",
        url:"/management/ordinary/department/query/all",
        dataType:"json",
        success:function (result) {
            var html = "<option value=\"" + "" + "\">" + "请选择" + "</option>";
            $.each(result.data,function (n,value) {
                html += "<option value=\"" + value.id + "\">" + value.name + "</option>";
            });
            $("#department").html(html);
        }
    });
}

function initPosition(){
    $.ajax({
        type:"post",
        url:"/management/ordinary/position/query/all",
        dataType:"json",
        success:function (result) {
            var html = "<option value=\"" + " " + "\">" + "请选择" + "</option>";
            $.each(result.data,function (n, value) {
                html += "<option value=\"" + value.id + "\">" + value.name + "</option>";
            });
            $("#position").html(html);
        }
    })
}