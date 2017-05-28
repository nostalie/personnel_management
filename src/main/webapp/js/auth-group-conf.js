/**
 * Created by nostalie on 17-5-15.
 */
$(document).ready(function () {
    initGroup();

    $("#groupName").change(function () {
        listAuth();
        queryUser();
    });

    $("#update").click(function () {
        update();
    });

    $("#delete").click(function () {
        delet();
    });

    $("#submit").click(function () {
        queryUsers(0,100);
    })
});

function initGroup() {
    $.ajax({
        type: "post",
        url: "management/super/auth/group/list",
        dataType: "json",
        success: function (result) {
            if (result.status == 0) {
                var html = "<option value=\"" + "" + "\">" + "请选择" + "</option>";
                $.each(result.data, function (n, value) {
                    html += "<option value=\"" + value.id + "\">" + value.groupName + "</option>";
                });
                $("#groupName").html(html);
            } else {
                alert(result.message);
            }
        }
    })
}

function listAuth() {
    $("#auth").css("display","block");
    $("#add_user").css("display","block");
    $(".user").css("display","block");
    var id = $("#groupName").val();
    $("#c1").prop("checked", false);
    $("#c2").prop("checked", false);
    $("#c3").prop("checked", false);
    $("#c4").prop("checked", false);
    $("#c5").prop("checked", false);
    $.ajax({
        type: "post",
        url: "management/super/auth/group/query/id",
        data: {id: id},
        dataType: "json",
        success: function (result) {
            if (result.status == 0) {
                $("#name").val(result.data.groupName);
                $("#updateTime").val(result.data.updateTime);
                var type = result.data.type;
                if ((type & 1) != 0) {
                    $("#c1").prop("checked", "checked");
                }
                if ((type & 2) != 0) {
                    $("#c2").prop("checked", "checked");
                }
                if ((type & 4) != 0) {
                    $("#c3").prop("checked", "checked");
                }
                if ((type & 8) != 0) {
                    $("#c4").prop("checked", "checked");
                }
                if ((type & 16) != 0) {
                    $("#c5").prop("checked", "checked");
                }
            } else {
                alert(result.message);
            }
        }
    });
}

function update() {
    var id = $("#groupName").val();
    var groupName = $("#name").val();
    var updateTime = $("#updateTime").val();
    var type = 0;
    var values = $("input[type='checkbox']");
    for (var i = 0; i < values.length; i++) {
        if (values[i].checked) {
            type = type | values[i].value;
        }
    }
    $.ajax({
        type: "post",
        url: "management/super/auth/group/update",
        data: {id: id, groupName: groupName, type: type, updateTime: updateTime},
        dataType: "json",
        success: function (result) {
                alert(result.message);
        }
    })
}

function delet() {
    var id = $("#groupName").val();
    var groupName = $("#name").val();
    $.ajax({
        type: "post",
        url: "management/super/auth/group/delete",
        data: {id: id, groupName: groupName},
        dataType: "json",
        success: function (result) {
                alert(result.message);
                initGroup();
        }
    })
}

function queryUser() {
    var id = $("#groupName").val();
    $.ajax({
        type: "post",
        url: "management/allot/query/user/group",
        data: {groupId: id},
        dataType: "json",
        success: function (result) {
            if (result.status == 0) {
                var html = "";
                $.each(result.data, function (n, item) {
                    var user = item.user;
                    html += "<tr>";
                    html += "<td>" + user.id + "</td>";
                    html += "<td>" + user.realName + "</td>";
                    html += "<td>" + user.userName + "</td>";
                    html += "<td>" + user.department.name + "</td>";
                    html += "<td>" + user.position.name + "</td>";
                    html += "<td>" + "<a href='#' onclick=\"deleteUser(" + user.id + ")\">删除</a>" + "</td>";
                    html += "</tr>";
                });
                $("#content").html(html);
            }
            if (result.status != 0) {
                alert(result.message);
            }
        }
    })
}

function deleteUser(userId) {
    var authId = $("#groupName").val();
    $.ajax({
        type: "post",
        url: "management/allot/auth/delete",
        data: {authId: authId, userId: userId},
        dataType: "json",
        success: function (result) {
            queryUser();
            alert(result.message);
        }
    })
}

function queryUsers(offset, limit) {
    var realName = $("#realName").val();
    var userName = $("#userName").val();
    var departmentId = $("#department").val();
    var positionId = $("#position").val();
    $.ajax({
        type:"post",
        url:"management/bu/super/user/query/all",
        data:{offset:offset,limit:limit,realName:realName,userName:userName,departmentId:departmentId,positionId:positionId},
        dataType:"json",
        success:function (result) {
            var html = "";
            $.each(result.data,function (i, user) {
                html += "<tr>";
                html += "<td>" + user.id + "</td>";
                html += "<td>" + user.realName + "</td>";
                html += "<td>" + user.userName + "</td>";
                html += "<td>" + user.department.name + "</td>";
                html += "<td>" + user.position.name + "</td>";
                html += "<td>" + "<a href='#' onclick=\"addUser(" + user.id + ")\">添加</a>" + "</td>";
                html += "</tr>";
            });
            $("#content2").html(html);
        }
    });
}

function addUser(userId) {
    var authId = $("#groupName").val();
    $.ajax({
        type: "post",
        url: "management/allot/auth/add",
        data: {authId: authId, userId: userId},
        dataType: "json",
        success: function (result) {
            queryUser();
            alert(result.message);
        }
    })
}