$(function() {
	var currentUrl = location.href;
	var defaultMenuId = "";
	
	$("ul.nav li[data-nav]:not(:empty)").not($(this)).each(function() {
        $(this).removeClass("on");
        $("#" + $(this).attr("data-nav")).hide();
    });
	
	if(currentUrl === undefined || currentUrl === null || currentUrl === "") {
		// default 활성화
		$("#dataMenu").addClass('on');
		$('#dataContent').toggle(true);
		$('#contentsWrap').toggle(true);
	} else {
		// 다른거 활성화
		if( currentUrl.indexOf("/data/list") >= 0) {
			if( currentUrl.indexOf("#search") >= 0) {
				$("#searchMenu").addClass('on');
				$('#searchContent').toggle(true);
			} else if( currentUrl.indexOf("#spatial") >= 0) {
				$("#spatialMenu").addClass('on');
				$('#spatialContent').toggle(true);
			} else if( currentUrl.indexOf("#simulation") >= 0) {
				$("#simulationMenu").addClass('on');
				$('#simulationContent').toggle(true);
			} else if( currentUrl.indexOf("#civilVoice") >= 0) {
				$("#civilVoiceMenu").addClass('on');
				$('#civilVoiceContent').toggle(true);
			} else if( currentUrl.indexOf("#userPolicy") >= 0) {
				$("#userPolicyMenu").addClass('on');
				$('#userPolicyContent').toggle(true);
			} else {
				$("#dataMenu").addClass('on');
				$('#dataContent').toggle(true);
			}
			$('#contentsWrap').toggle(true);
		} else {
			$("#converterMenu").addClass('on');
			//$('#contentsWrap').toggle(true);
		}
	}
	
	// 상세 메뉴 닫기
	$('button#closeLeftBtn').click(function() {
		//$('ul.nav li[data-nav]').removeClass('on');
		
		$('#contentsWrap').hide();
		$('ul.nav li.on').removeClass('on');
		//$('#closeLeftBtn').toggle();
	});

	/***** NAV WRAP: 메뉴 *****/
    // 상세 메뉴 클릭 시 기본 동작
    $("ul.nav li[data-nav]:not(:empty)").click(function() {
        var active = $(this).attr('data-nav');
        var display = $(this).toggleClass('on').hasClass('on');
        
        // 변환(upload-data)이 아닌 컨텐츠 클릭시 다시 지도 페이지로 돌아감 
        if(location.href.indexOf("upload") > 0 || location.href.indexOf("converter") > 0 || location.href.indexOf("group") > 0) {
        	$(this).removeClass('on');
        	var classId = $(this).attr('class');
        	window.location="../data/list#" + classId;
        }
        
        // 변환 클릭 이벤트시 url 변경 
        if(active === "converterContent") {
        	window.location="../upload-data/list";
        }
        
        $("ul.nav li[data-nav]:not(:empty)").not($(this)).each(function() {
            $(this).removeClass('on');
            $('#' + $(this).attr('data-nav')).hide();
        });

        $('#'+ active).toggle(display);
        $('#contentsWrap').toggle(display);
    });

    
/***** Contents Wrap: 공간분석 *****/	
	// 공간분석 그룹 클릭 시	
	$('#spatialContent ul.listDrop li > p').click(function(e) {
		var parentObj = $(this).parent();
		var index = parentObj.index();
		$('#spatialContent ul.listDrop > li').eq(index).toggleClass('on');
	});
	
	// 공간분석 위치 관련 버튼 클릭 시
	$('#spatialContent button[class*="draw"]').click(function(e) {
		$(this).toggleClass('on');
		
		$('#spatialContent button[class*="draw"]').not($(this)).each(function(i,a){
			$(this).removeClass('on');
		});
	});
	
/***** Contents Wrap: 공간분석 *****/	
	// 시뮬레이션 그룹 클릭 시	
	$('#simulationContent ul.listDrop li > p').click(function() {
		var parentObj = $(this).parent();
		var index = parentObj.index();
		$('#simulationContent ul.listDrop > li').eq(index).toggleClass('on');
	});
	
	// 행정구역 검색
	$('div.district').hover(function() {
		$('div.districtWrap').css('display', 'block');
	}, function(){
		$('div.districtWrap').css('display', 'none');
	});

	$('div.districtWrap').hover(function() {
		$('div.districtWrap').css('display', 'block');
	}, function(){
		$('div.districtWrap').css('display', 'none');
	});
});

function allMenuDisplay() {
	$("ul.nav li[data-nav]:not(:empty)").not($(this)).each(function() {
        $(this).removeClass('on');
        $('#' + $(this).attr('data-nav')).hide();
    });

    $('#'+ active).toggle(display);
    $('#contentsWrap').toggle(display);
}