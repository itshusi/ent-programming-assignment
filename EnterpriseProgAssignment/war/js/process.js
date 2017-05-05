$(document).ready(function() {

    var restPost = false;
    var restPut = false;
    var restDelete = false;

    $.ajaxSetup({
        cache: false
    });

    $(document).ajaxSend(function(event, xhr, settings) {
        requestUrl = settings.url;
    });

    var uri = "http://1-dot-enterprise-programming-1.appspot.com/api";
    //var uri = "http://localhost:8888/api";
    var requestUrl;

    var clearResponse = function(id) {
        $(id).empty();
    }

    var getRequestUrl = function() {
        var rqstUrl = requestUrl.split("?_=");
        if (rqstUrl.length > 1) {
            return rqstUrl[0]

        } else {
            rqstUrl = requestUrl.split("&_=")
            return rqstUrl[0];
        }

    }

    //Retrieve all courses
    var retrieveAllCourses = function(dataType) {

        if ($('#allRest').is(':checked')) {
            $.ajax({
                url: uri + "/rest/retrievecourses/" + dataType,
                type: 'GET',
                dataType: 'text',
                success: function(data) {
                    $('#allCoursesRequest').text(getRequestUrl());

                    $('#allOutput').text(data);

                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#allCoursesRequest').text(getRequestUrl());
                    $('#allOutput').text(textStatus);
                }
            });
        } else {

            var data = {
                dataType: dataType
            }

            $.ajax({
                url: uri + "/retrieveCourses",
                type: 'GET',
                dataType: 'text',
                data: data,
                success: function(data) {
                    $('#allCoursesRequest').text(getRequestUrl());

                    $('#allOutput').text(data);

                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#allCoursesRequest').text(getRequestUrl());
                    $('#allOutput').text(textStatus);
                }
            });
        }
    }

    $('#btnRetrieveCourses').click(function() {

        retrieveAllCourses('json');
    });

    $("#allCourseJson").click(function() {
        clearResponse('#allOutput');
        retrieveAllCourses('json');
    });

    $("#allCourseXml").click(function() {
        clearResponse('#allOutput');
        retrieveAllCourses('xml');
    });

    $("#allCourseText").click(function() {
        clearResponse('#allOutput');
        retrieveAllCourses('text');
    });


    $("#clearAllCourses").click(function() {
        clearResponse('#allOutput');
        $('#allCoursesRequest').empty();
    });

    //Search courses
    var searchCourses = function(dataType) {

        var searchName = $('#txtSearchTerm').val();


        if ($('#searchRest').is(':checked')) {

            $.ajax({
                url: uri + "/rest/searchcourses/" + searchName + "/" + dataType,
                type: 'GET',
                dataType: 'text',
                success: function(data) {
                    //strip timestap from the request url
                    $('#searchRequest').text(getRequestUrl());

                    $('#searchOutput').text(data);

                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#searchRequest').text(getRequestUrl());
                    $('#searchOutput').text(jqXHR.responseText);
                }
            });

        } else {

            var data = {
                dataType: dataType,
                searchName: searchName
            }


            $.ajax({
                url: uri + "/searchCourses",
                type: 'GET',
                dataType: 'text',
                data: data,
                success: function(data) {
                    $('#searchRequest').text(getRequestUrl());

                    $('#searchOutput').text(data);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#searchRequest').text(getRequestUrl());
                    $('#searchOutput').text(jqXHR.responseText);
                }
            });
        }
    }


    $('#btnSearchCourses').click(function() {
        clearResponse('#searchOutput');
        searchCourses('json');
    });

    $('#btnJsonSearch').click(function() {
        clearResponse('#searchOutput');
        searchCourses('json');
    });

    $('#btnXmlSearch').click(function() {
        clearResponse('#searchOutput');
        searchCourses('xml');
    });

    $('#btnTextSearch').click(function() {
        clearResponse('#searchOutput');
        searchCourses('text');
    });

    $("#clearSearchCourses").click(function() {
        clearResponse('#searchOutput');
        $('#txtSearchTerm').val('');
        $('#searchRequest').empty();
    });


    //Add a course
    $('#btnAddCourse').click(function() {
        var format = $('#addFormat').val();
        if (format == 'text') {
            addTextCourse();
        } else if (format == 'json') {
            addJsonCourse();
        } else if (format == 'xml') {
            addXmlCourse();
        }
        clearResponse('#addOutput');
        $('#name').val('');
        $('#description').val('');
        $('#degreeLevel').val('');
        $('#courseYear').val('');
        $('#ucasCode').val('');
        $('#length').val('');
        $('#addRequest').empty();
    });


    var addTextCourse = function() {
         if ($('#addRest').is(':checked')) {
            $.ajax({
            url: uri + "/rest/addcourses/text/" + $('#name').val() + "/" + $('#description').val() + "/" + $('#degreeLevel').val() + "/" + $('#courseYear').val() + "/" + $('#ucasCode').val() + "/" + $('#length').val(),
            type: 'POST',
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                $('#addRequest').text(getRequestUrl());
                $('#addOutput').text('Response: ' + data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#addRequest').text(getRequestUrl());
                $('#addOutput').text(jqXHR.responseText);
            }

        });
         }else {
        $.ajax({
            url: uri + "/addCourses",
            type: 'POST',
            data: "dataType=text&name=" + $('#name').val() + "&description=" + $('#description').val() + "&degreeLevel=" + $('#degreeLevel').val() + "&courseYear=" + $('#courseYear').val() + "&ucasCode=" + $('#ucasCode').val() + "&length=" + $('#length').val(),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                $('#addRequest').text(getRequestUrl());
                $('#addOutput').text('Response: ' + data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#addRequest').text(getRequestUrl());
                $('#addOutput').text(jqXHR.responseText);
            }

        });
        }
    }


    var addJsonCourse = function() {
        var name = $('#name').val();
        var description = $('#description').val();
        var degreeLevel = $('#degreeLevel').val();
        var courseYear = $('#courseYear').val();
        var ucasCode = $('#ucasCode').val();
        var length = $('#length').val();
        
        var courseStr = {name:name,description:description,degreeLevel:degreeLevel,courseYear:courseYear,ucasCode:ucasCode,length:length};

         if ($('#addRest').is(':checked')) {
$.ajax({
            url: uri + "/rest/addcourses/json/",
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(courseStr),
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                $('#addRequest').text(getRequestUrl());
                $('#addOutput').text("Data " + JSON.stringify(courseStr) + '\n\n' + 'Response: ' + data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#addRequest').text(getRequestUrl());
                $('#addOutput').text(jqXHR.responseText);
            }

        });
         } else {
        $.ajax({
            url: uri + "/addCourses?dataType=json&data="+JSON.stringify(courseStr),
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                $('#addRequest').text(getRequestUrl());
                $('#addOutput').text("Data " + JSON.stringify(courseStr) + '\n\n' + 'Response: ' + data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#addRequest').text(getRequestUrl());
                $('#addOutput').text(jqXHR.responseText);
            }

        });
        }
    }

    var addXmlCourse = function() {
        var name = $('#name').val();
        var description = $('#description').val();
        var degreeLevel = $('#degreeLevel').val();
        var courseYear = $('#courseYear').val();
        var ucasCode = $('#ucasCode').val();
        var length = $('#length').val();

        var courseStr = '<Course><name>' + name + '</name><description>' + description + '</description><degreeLevel>' + degreeLevel + '</degreeLevel><courseYear>' + courseYear + '</courseYear><ucasCode>' + ucasCode + '</ucasCode><length>' + length + '</length></Course>';

        if ($('#addRest').is(':checked')) {
            $.ajax({
                url: uri + "/rest/addcourses/xml",
                type: 'POST',
                dataType: 'text',
                data: courseStr,
                processData: false,
                contentType: 'application/xml',
                success: function(data, textStatus, jqXHR) {
                    $('#addRequest').text(getRequestUrl());
                    $('#addOutput').text("Data " + courseStr + '\n\n' + 'Response: ' + data);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#addRequest').text(getRequestUrl());
                    $('#addOutput').text(jqXHR.responseText);
                }
            });
        } else {
            $.ajax({
                url: uri + "/addCourses?dataType=xml&data="+courseStr,
                type: 'POST',
                dataType: 'text',
                contentType: 'application/xml',
                processData: false,
                success: function(data, textStatus, jqXHR) {
                    $('#addRequest').text(getRequestUrl());
                    $('#addOutput').text("Data " + courseStr + '\n\n' + 'Response: ' + data);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#addRequest').text(getRequestUrl());
                    $('#addOutput').text(jqXHR.responseText);
                }

            });

        }
    }

    $("#clearAddCourse").click(function() {
        clearResponse('#addOutput');
        $('#name').val('');
        $('#description').val('');
        $('#degreeLevel').val('');
        $('#courseYear').val('');
        $('#ucasCode').val('');
        $('#length').val('');
        $('#addRequest').empty();
    });


//Delete a course
    $('#clearDeleteCourse').click(function() {

        $('#txtDelId').val('');
        clearResponse('#deleteRequest');
        clearResponse('#deleteOutput');

    });

    $('#btnDeleteCourse').click(function() {
        var format = $('#deleteFormat').val();
        if (format == 'text') {
            deleteTextCourse();
        } else if (format == 'json') {
            deleteJsonCourse();
        } else if (format == 'xml') {
            deleteXmlCourse();
        }

    });


   var deleteTextCourse = function() {
         if ($('#deleteRest').is(':checked')) {
            $.ajax({
                url: uri + "/rest/deletecourses/text/" + $('#txtDeleteId').val(),
                type: 'DELETE',
                dataType: 'text',
                success: function(data, textStatus, jqXHR) {
                    $('#deleteRequest').text(getRequestUrl());
                    $('#deleteOutput').text('Response: ' + data);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#deleteRequest').text(getRequestUrl());
                    $('#deleteOutput').text(jqXHR.responseText);
                }
            });
        } else {
        $.ajax({
            url: uri + "/deleteCourses?dataType=text&id=" + $('#txtDeleteId').val(),
            type: 'DELETE',
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                    $('#deleteRequest').text(getRequestUrl());
                    $('#deleteOutput').text('Response: ' + data);
                },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#deleteRequest').text(getRequestUrl());
                $('#deleteOutput').text(jqXHR.responseText);
            }

        });
    }
    }

 
    var deleteJsonCourse = function() {

        var deletestr = { id: $('#txtDeleteId').val() };
 if ($('#deleteRest').is(':checked')) {
            $.ajax({
                url: uri + "/rest/deletecourses/json",
                type: 'DELETE',
                dataType: 'text',
                data: JSON.stringify(deletestr),
                contentType: "application/json; charset=utf-8",
                success: function(data, textStatus, jqXHR) {
                    $('#deleteRequest').text(getRequestUrl());
                    $('#deleteOutput').text("Data " + JSON.stringify(deletestr) + '\n\n' + 'Response: ' + data);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#deleteRequest').text(getRequestUrl());
                    $('#deleteOutput').text(jqXHR.responseText);
                }
            });
        } else {
        $.ajax({
            url: uri + "/deleteCourses?dataType=json&data="+JSON.stringify(deletestr),
            type: 'DELETE',
            contentType: "application/json; charset=utf-8",
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                $('#deleteRequest').text(getRequestUrl());
                $('#deleteOutput').text("Request body: " + JSON.stringify(deletestr) + '\n\n' + 'Response: ' + data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#deleteRequest').text(getRequestUrl());
                $('#deleteOutput').text(jqXHR.responseText);
            }

        });
    }
    };


 
    var deleteXmlCourse = function() {

        var id = $('#txtDeleteId').val();

        var deletestr = '<CourseDelete><id>' + id + '</id></CourseDelete>';
 if ($('#deleteRest').is(':checked')) {
            $.ajax({
                url: uri + "/rest/deletecourses/xml",
                type: 'DELETE',
                dataType: 'text',
                data: deletestr,
                processData: false,
                contentType: 'application/xml',
                success: function(data, textStatus, jqXHR) {
                $('#deleteRequest').text(getRequestUrl());
                $('#deleteOutput').text("Request body: \n" + deletestr + '\n\n' + 'Response: ' + data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#deleteRequest').text(getRequestUrl());
                $('#deleteOutput').text(jqXHR.responseText);
            }
            });
        } else {
        $.ajax({
            url: uri + "/deleteCourses?dataType=xml&data="+deletestr,
            type: 'DELETE',
            dataType: 'text',
            processData: false,
            contentType: 'application/xml',
            success: function(data, textStatus, jqXHR) {
                $('#deleteRequest').text(getRequestUrl());
                $('#deleteOutput').text("Request body: \n" + deletestr + '\n\n' + 'Response: ' + data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#deleteRequest').text(getRequestUrl());
                $('#deleteOutput').text(jqXHR.responseText);
            }

        });
    }

    };

    //Update a Course
    $('#clearUpdateCourse').click(function() {

        $('#txtUpdateId').val('');
        $('#selectAttribute').val('');
        $('#txtUpdateVal').val('');
        clearResponse('#updateRequest');
        clearResponse('#updateOutput');

    });

    $('#btnUpdateCourse').click(function() {
        var format = $('#updateFormat').val();
        if (format == 'text') {
            updateTextCourse();
        } else if (format == 'json') {
            updateJsonCourse();
        } else if (format == 'xml') {
            updateXmlCourse();
        }

    });


    var updateTextCourse = function() {
         if ($('#updateRest').is(':checked')) {
            $.ajax({
                url: uri + "/rest/updatecourses/text/" + $('#txtUpdateId').val() +"/"+ $('#selectAttribute').val() + "/" + $('#txtUpdateVal').val(),
                type: 'PUT',
                dataType: 'text',
                success: function(data, textStatus, jqXHR) {
                    $('#updateRequest').text(getRequestUrl());
                    $('#updateOutput').text('Response: ' + data);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#updateRequest').text(getRequestUrl());
                    $('#updateOutput').text(jqXHR.responseText);
                }
            });
        } else {
        $.ajax({
            url: uri + "/updateCourses?dataType=text&id=" + $('#txtUpdateId').val() + "&attribute=" + $('#selectAttribute').val() + "&updateValue=" + $('#txtUpdateVal').val(),
            type: 'PUT',
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                    $('#updateRequest').text(getRequestUrl());
                    $('#updateOutput').text('Response: ' + data);
                },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#updateRequest').text(getRequestUrl());
                $('#updateOutput').text(jqXHR.responseText);
            }

        });
    }
    }



    var updateJsonCourse = function() {

        var updatestr = { id: $('#txtUpdateId').val(), attribute: $('#selectAttribute').val(), updateValue: $('#txtUpdateVal').val() };
 if ($('#updateRest').is(':checked')) {
            $.ajax({
                url: uri + "/rest/updatecourses/json",
                type: 'PUT',
                dataType: 'text',
                data: JSON.stringify(updatestr),
                contentType: "application/json; charset=utf-8",
                success: function(data, textStatus, jqXHR) {
                    $('#updateRequest').text(getRequestUrl());
                    $('#updateOutput').text("Data " + JSON.stringify(updatestr) + '\n\n' + 'Response: ' + data);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#updateRequest').text(getRequestUrl());
                    $('#updateOutput').text(jqXHR.responseText);
                }
            });
        } else {
        $.ajax({
            url: uri + "/updateCourses?dataType=json&data="+JSON.stringify(updatestr),
            type: 'PUT',
            contentType: "application/json; charset=utf-8",
            dataType: 'text',
            success: function(data, textStatus, jqXHR) {
                $('#updateRequest').text(getRequestUrl());
                $('#updateOutput').text("Request body: " + JSON.stringify(updatestr) + '\n\n' + 'Response: ' + data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#updateRequest').text(getRequestUrl());
                $('#updateOutput').text(jqXHR.responseText);
            }

        });
    }
    };


    var updateXmlCourse = function() {

        var id = $('#txtUpdateId').val();
        var attribute = $('#selectAttribute').val();
        var updateValue = $('#txtUpdateVal').val();

        var updatestr = '<CourseUpdate><id>' + id + '</id><attribute>' + attribute + '</attribute><updateValue>' + updateValue + '</updateValue></CourseUpdate>';
 if ($('#updateRest').is(':checked')) {
            $.ajax({
                url: uri + "/rest/updatecourses/xml",
                type: 'PUT',
                dataType: 'text',
                data: updatestr,
                processData: false,
                contentType: 'application/xml',
                success: function(data, textStatus, jqXHR) {
                $('#updateRequest').text(getRequestUrl());
                $('#updateOutput').text("Request body: \n" + updatestr + '\n\n' + 'Response: ' + data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#updateRequest').text(getRequestUrl());
                $('#updateOutput').text(jqXHR.responseText);
            }
            });
        } else {
        $.ajax({
            url: uri + "/updateCourses?dataType=xml&data="+updatestr,
            type: 'PUT',
            dataType: 'text',
            processData: false,
            contentType: 'application/xml',
            success: function(data, textStatus, jqXHR) {
                $('#updateRequest').text(getRequestUrl());
                $('#updateOutput').text("Request body: \n" + updatestr + '\n\n' + 'Response: ' + data);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#updateRequest').text(getRequestUrl());
                $('#updateOutput').text(jqXHR.responseText);
            }

        });
    }

    };

});