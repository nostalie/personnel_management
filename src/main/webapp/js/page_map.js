/**
 * Created by nostalie on 17-5-12.
 */
$(document).ready(function () {
    $("#user_info").click(function () {
        $("#frame").attr("src", "user_info.html");
    });

    $("#user_search").click(function () {
        $("#frame").attr("src", "user_search.html");
    });

    $("#user_add").click(function () {
        $("#frame").attr("src","user_add.html");
    });

    $("#auth_group_add").click(function () {
        $("#frame").attr("src","auth_group_add.html");
    });

    $("#auth_group_conf").click(function () {
        $("#frame").attr("src","auth_group_conf.html");
    });

    $("#department_add").click(function () {
        $("#frame").attr("src","department_add.html");
    });

    $("#position_add").click(function () {
        $("#frame").attr("src","position_add.html");
    })
});
