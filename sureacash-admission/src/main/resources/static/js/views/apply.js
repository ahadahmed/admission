var base;
var studentInfo;
var academicInfo;

function validateAcademicData() {
    hideAlert();
    var form = $("#form-academic-info");
    if (form.valid()) {
        academicInfo = {
            "securityCode": 68545,
            "sscInformation": {
                "board": $('#ssc_board').val(),
                "roll": $('#ssc_rollno').val(),
                "regNo": $('#ssc_registration_no').val(),
                "passingYear": $('#ssc_passing_year').val()
            },
            "hscInformation": {
                "board": $('#hsc_board').val(),
                "roll": $('#hsc_rollno').val(),
                "regNo": $('#hsc_registration_no').val(),
                "passingYear": $('#hsc_passing_year').val()
            }
        };

        startLoader();
        $.ajax({
            url: base + 'academic/validate-info',
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(academicInfo),
            success: function (response) {
                studentInfo = response;
                fillUnitTable(studentInfo.unitInfo);
                $("#div-academic-info").hide();
                $("#div-unit-selection").show();
            },
            error: function (response) {
                showErrorMessage("Error loading data");
            },
            complete: function () {
                stopLoader();
            }
        });
    }
}

function fillUnitTable (unitInfos) {
    for (var i = 0; i < unitInfos.length; i++) {
        $("#tbl-unit-selection").find("tbody")
        .append($("<tr>")
                .append($("<td>").append($("<input>").attr("type", "checkbox").addClass("unit").val(unitInfos[i].id)))
                .append($("<td>").text(unitInfos[i].unitName))
                .append($("<td>").text(unitInfos[i].unitDescription))
                .append($("<td>").text(unitInfos[i].fees))
        );
    }
}

function showPersonalInfo() {
    hideAlert();
    var selectedUnitList = $('input[type=checkbox][class=unit]:checked').map(function(_, el) {
        return $(el).val();
    }).get();
    if(selectedUnitList.length > 0){
        $("#div-unit-selection").hide();
        $("#div-personal-info").show();
    } else{
        showErrorMessage('You have to select at least one unit for next step')
    }
}

function backToUnitSelection() {
    hideAlert();
    $("#div-personal-info").hide();
    $("#div-unit-selection").show();
}

function showPreview() {
    hideAlert();
    if ($("#form-personal-info").valid()) {
        var personal_info = {
            "mobile": $("#mobile_no").val(),
            "emial": $("#email_id").val(),
            "password": $("#password_id").val()
        };
        studentInfo["personal_info"] = personal_info;
        appendTOApplicationDiv();
        $("#div-personal-info").hide();
        $('#div-application-preview').show();
    }
}

function backToPersonalInfo() {
    hideAlert();
    $('#div-application-preview').hide();
    $("#div-personal-info").show();
}

function appendTOApplicationDiv() {
    $('#trow_applicant_name_id').html(studentInfo.name);
    $('#trow_father_name_id').html(studentInfo.fatherName);
    $('#trow_mother_name_id').html(studentInfo.motherName);
    $('#trow_quota_id').html($('input[name=quota]:checked').val());
    $('#trow_email_id').html(studentInfo.personal_info.emial);
    $('#trow_number_id').html(studentInfo.personal_info.mobile);

    $("#tbl-academic-preview").find("tbody")
    .append($("<tr>")
            .append($("<td>").text("SSC/Equivalent"))
            .append($("<td>").text(studentInfo.sscInfo.board))
            .append($("<td>").text(studentInfo.sscInfo.roll))
            .append($("<td>").text(studentInfo.sscInfo.passingYear))
            .append($("<td>").text(studentInfo.sscInfo.group))
            .append($("<td>").text(studentInfo.sscInfo.gpa)))
    .append($("<tr>")
            .append($("<td>").text("HSC/Equivalent"))
            .append($("<td>").text(studentInfo.hscInfo.board))
            .append($("<td>").text(studentInfo.hscInfo.roll))
            .append($("<td>").text(studentInfo.hscInfo.passingYear))
            .append($("<td>").text(studentInfo.hscInfo.group))
            .append($("<td>").text(studentInfo.hscInfo.gpa))
    );

    var totalFees = 0;
    var selectedUnitIdList = $('input[type=checkbox][class=unit]:checked').map(
            function (_, el) {
                return $(el).val();
            }).get();
    var tblUnitPreview = $('#tbl-unit-preview');
    for (var itr = 0; itr < studentInfo.unitInfo.length; itr++) {
        var id = studentInfo.unitInfo[itr].id.toString();
        if (selectedUnitIdList.includes(id)) {
            tblUnitPreview.append($("<tr>")
                .append($("<td>").text(studentInfo.unitInfo[itr].unitName))
                .append($("<td>").text(studentInfo.unitInfo[itr].unitDescription))
                .append($("<td>").text(studentInfo.unitInfo[itr].fees))
            );
            totalFees = totalFees + parseInt(studentInfo.unitInfo[itr].fees);
        }
    }
    var formattedFees = $.number(totalFees, 2);
    tblUnitPreview.append($("<tr>").addClass("bold")
            .append($("<td>").attr("colspan", 2).addClass("text-center").text("Total Payment Amount"))
            .append($("<td>").text(formattedFees))
    );
}

function submitApplication() {
    hideAlert();
    var unit_list = [];
    unit_list = $('input[type=checkbox][class=unit]:checked').map(function(_, el) {
        return $(el).val();
    }).get();

    var request = {
        "name": studentInfo.name,
        "fatherName": studentInfo.fatherName,
        "motherName": studentInfo.motherName,
        "sscInfo": {
            "board": studentInfo.sscInfo.board,
            "roll": studentInfo.sscInfo.roll,
            "regNo": studentInfo.sscInfo.regNo,
            "passingYear": studentInfo.sscInfo.passingYear,
            "gpa": studentInfo.sscInfo.gpa,
            "group": studentInfo.sscInfo.group
        },
        "hscInfo": {
            "board": studentInfo.hscInfo.board,
            "roll": studentInfo.hscInfo.roll,
            "regNo": studentInfo.hscInfo.regNo,
            "passingYear": studentInfo.hscInfo.passingYear,
            "gpa": studentInfo.hscInfo.gpa,
            "group": studentInfo.hscInfo.group
        },
        "quota": $('input[name=quota]:checked').val(),
        "email": studentInfo.personal_info.email,
        "mobile": studentInfo.personal_info.mobile,
        "unitList": unit_list,
        "password": studentInfo.personal_info.password
    };

    startLoader();
    $.ajax({
        url: base + 'academic/registration',
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(request),
        success: function (response) {
            $('#confirm-username-id').html(response.userName);
            $('#confirm-password-id').html(response.password);
            $('#div-application-preview').hide();
            $('#div-application-confirmation').show();
        },
        error: function (response) {
            showErrorMessage("Error submitting application");
        },
        complete: function () {
            stopLoader();
        }
    });
}

function showErrorMessage(message) {
    $('#error-message-id').html(message);
    $('#alert-id').show();
}

function hideAlert() {
    $('#alert-id').hide();
}

function startLoader() {
    $(".loader").each(function () {
        $(this).css("display", "block");
    });
}

function stopLoader() {
    $(".loader").each(function () {
        $(this).css("display", "none");
    });
}