$(function() {
    renderJobs();
    //renderBreadCrumbMenu();
    bindButtons();
});

function renderJobs() {
    var ip = $("#server-ip").text();
    $("#server-jobs-tbl").bootstrapTable({
        url: "/servers/jobs?serverIp=" + ip,
        cache: false,
        search: true,
        showRefresh: true,
        showColumns: true
    }).on("all.bs.table", function() {
        doLocale();
    });
}

function statusFormatter(value, row) {
    if (0 === row.instanceCount ) {
        return "<span class='label label-default' data-lang='status-offline'></span>";
    }
    switch(value) {
        case "OK":
            return "<span class='label label-success' data-lang='status-enabled'></span>";
            break;
        case "DISABLED":
            return "<span class='label label-warning' data-lang='status-disabled'></span>";
            break;
    }
}

function generateOperationButtons(val, row) {
    if (0 === row.instanceCount ) {
        return "<button operation='remove-server-job' class='btn-xs btn-danger' job-name='" + row.jobName + "' data-lang='operation-remove'></button>";
    }
    var disableButton = "<button operation='disable-server-job' class='btn-xs btn-warning' ip='" + row.ip + "' job-name='" + row.jobName + "' data-lang='operation-disable'></button>";
    var enableButton = "<button operation='enable-server-job' class='btn-xs btn-success' ip='" + row.ip + "' job-name='" + row.jobName + "' data-lang='operation-enable'></button>";
    var shutdownButton = "<button operation='shutdown-server-job' class='btn-xs btn-danger' job-name='" + row.jobName + "' data-lang='operation-shutdown'></button>";
    var operationTd = "";
    if ("DISABLED" === row.status) {
        operationTd = enableButton + "&nbsp;" + shutdownButton;
    } else {
        operationTd = disableButton + "&nbsp;" + shutdownButton;
    }
    return operationTd;
}

function bindButtons() {
    bindDisableButton();
    bindEnableButton();
    bindShutdownButton();
    bindRemoveButton();
}

function bindDisableButton() {
    $(document).off("click", "button[operation='disable-server-job'][data-toggle!='modal']");
    $(document).on("click", "button[operation='disable-server-job'][data-toggle!='modal']", function(event) {
        $.ajax({
            url: "/servers/" + $("#server-ip").text() + "/jobs/" + $(event.currentTarget).attr("job-name") + "/disable",
            type: "POST",
            success: function() {
                $("#server-jobs-tbl").bootstrapTable("refresh");
                showSuccessDialog();
            }
        });
    });
}

function bindEnableButton() {
    $(document).off("click", "button[operation='enable-server-job'][data-toggle!='modal']");
    $(document).on("click", "button[operation='enable-server-job'][data-toggle!='modal']", function(event) {
        $.ajax({
            url: "/servers/" + $("#server-ip").text() + "/jobs/" + $(event.currentTarget).attr("job-name") + "/enable",
            type: "DELETE",
            success: function() {
                $("#server-jobs-tbl").bootstrapTable("refresh");
                showSuccessDialog();
            }
        });
    });
}

function bindShutdownButton() {
    $(document).off("click", "button[operation='shutdown-server-job'][data-toggle!='modal']");
    $(document).on("click", "button[operation='shutdown-server-job'][data-toggle!='modal']", function(event) {
        showShutdownConfirmModal();
        var serverIp = $("#server-ip").text();
        var jobName = $(event.currentTarget).attr("job-name");
        $(document).off("click", "#confirm-btn");
        $(document).on("click", "#confirm-btn", function() {
            $.ajax({
                url: "/servers/" + serverIp + "/jobs/" + jobName + "/shutdown",
                type: "POST",
                success: function () {
                    $("#confirm-dialog").modal("hide");
                    $(".modal-backdrop").remove();
                    $("body").removeClass("modal-open");
                    $("#server-jobs-tbl").bootstrapTable("refresh");
                }
            });
        });
    });
}

function bindRemoveButton() {
    $(document).off("click", "button[operation='remove-server-job'][data-toggle!='modal']");
    $(document).on("click", "button[operation='remove-server-job'][data-toggle!='modal']", function(event) {
        showDeleteConfirmModal();
        var serverIp = $("#server-ip").text();
        var jobName = $(event.currentTarget).attr("job-name");
        $(document).off("click", "#confirm-btn");
        $(document).on("click", "#confirm-btn", function() {
            $.ajax({
                url: "/servers/config/" + serverIp + "/jobs/" + jobName,
                type: "DELETE",
                success: function () {
                    $("#confirm-dialog").modal("hide");
                    $(".modal-backdrop").remove();
                    $("body").removeClass("modal-open");
                    //refreshServerNavTag();
                    $("#server-jobs-tbl").bootstrapTable("refresh");
                }
            });
        });
    });
}

function renderBreadCrumbMenu() {
    $("#breadcrumb-server").click(function() {
        $("#servers-status-overview-dev").load("/worker/servers", null, function(){
            doLocale();
        });
    });
}
