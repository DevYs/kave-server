var page = 1;

var contentsTemp = "<div class='idx-thumb col-xs-4 col-sm-3 col-md-2'>" +
                    "<a href='/contents?contentsNo=${contentsNo}'>" +
                        "<img class='img-responsive' src='${contentsPosterUrl}' alt='${alt}' />" +
                    "</a>" +
                    "<a class='idx-thumb-tl' href='/contents?contentsNo=${contentsNo}'>${contentsName}</a>" +
                    "<a class='idx-thumb-tl' href='/contents?contentsNo=${contentsNo}'>${releaseDate}</a>" +
                "</div>";

$(document).ready(function() {
    $("#next").click(function() {
        $(this).attr("disabled", "disabled");

        page = page + 1;

        $.ajax({
            url : "/api/search?pageNo=" + page + "&searchWord=" + $("#searchWord").val() + "&channelNo=" + $("#channelNo").val(),
            success : function(contentsList){
                if(contentsList.length < 1) {
                    $("#next").hide();
                    return;
                }

                for(var i=0; i<contentsList.length; i++) {
                    var c = contentsTemp
                    .replace("${contentsNo}", contentsList[i].contentsNo)
                    .replace("${contentsNo}", contentsList[i].contentsNo)
                    .replace("${contentsNo}", contentsList[i].contentsNo)
                    .replace("${contentsPosterUrl}", contentsList[i].contentsPosterUrl)
                    .replace("${contentsName}", contentsList[i].contentsName)
                    .replace("${releaseDate}", contentsList[i].releaseDate)
                    .replace("${alt}", contentsList[i].contentsName + " 포스터 이미지");
                    $("#contentsList").append(c);
                }

                $("#next").removeAttr("disabled");
            }
        })
    });
});

