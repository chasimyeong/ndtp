<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>
<%@ include file="/WEB-INF/views/common/config.jsp" %>

<!DOCTYPE html>
<html lang="${accessibility}">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width">
	<title>데이터 수정 | NDTP</title>
	
	<link rel="stylesheet" href="/externlib/cesium/Widgets/widgets.css" />
	<link rel="stylesheet" href="/externlib/jquery-ui-1.12.1/jquery-ui.min.css" />
	<link rel="stylesheet" href="/images/${lang}/icon/glyph/glyphicon.css" />
	<link rel="stylesheet" href="/css/${lang}/user-style.css" />
	<link rel="stylesheet" href="/css/${lang}/style.css" />
	<script type="text/javascript" src="/externlib/jquery-3.3.1/jquery.min.js"></script>
	<script type="text/javascript" src="/externlib/jquery-ui-1.12.1/jquery-ui.min.js"></script>
</head>
<body>

<%@ include file="/WEB-INF/views/layouts/header.jsp" %>

<div id="wrap">
	<!-- S: NAVWRAP -->
	<div class="navWrap">
	 	<%@ include file="/WEB-INF/views/layouts/menu.jsp" %> 
	</div>
	<!-- E: NAVWRAP -->
	
	<div class="container" style="float:left; width: calc(100% - 78px);">
		<div style="padding: 20px 20px 0px 10px; font-size: 18px;">3D 업로딩 데이터 자동 변환</div>
		<div class="tabs" >
			<ul class="tab">
				<li onclick="location.href='/data-group/list'">데이터 그룹</li>
				<li onclick="location.href='/data-group/input'">데이터 그룹 등록</li>
				<li onclick="location.href='/upload-data/input'">업로딩 데이터</li>
			   	<li onclick="location.href='/upload-data/list'">업로딩 데이터 목록</li>
			  	<li onclick="location.href='/converter/list'">업로딩 데이터 변환 목록</li>
			  	<li onclick="location.href='/data/list'" class="on">데이터 목록</li>
			</ul>
		</div>
		<form:form id="dataInfo" modelAttribute="dataInfo" method="post" onsubmit="return false;">
		<table class="input-table scope-row">
			<col class="col-label l" />
			<col class="col-input" />
			<tr>
				<th class="col-label" scope="row">
					데이터 그룹명
				</th>
				<td class="col-input">
					${dataInfo.dataGroupName }
				</td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
					데이터  Key
				</th>
				<td class="col-input">
					${dataInfo.dataKey }
				</td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
					데이터명
				</th>
				<td class="col-input">
					${dataInfo.dataName }
				</td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
	            	공유 타입
				</th>
	            <td class="col-input">
	<c:if test="${dataInfo.sharing eq 'common'}">공통</c:if>
	<c:if test="${dataInfo.sharing eq 'public'}">공개</c:if>
	<c:if test="${dataInfo.sharing eq 'private'}">개인</c:if>
	<c:if test="${dataInfo.sharing eq 'group'}">그룹</c:if>
				</td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
	            	<form:label path="mappingType">매핑 타입</form:label>
	                <span class="icon-glyph glyph-emark-dot color-warning"></span>
				</th>
	            <td class="col-input">
					<select id="mappingType" name="mappingType" class="selectBoxClass">
						<option value="origin">origin</option>
						<option value="boundingboxcenter ">boundingboxcenter</option>
					</select>
				</td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
					<form:label path="longitude">경도/위도/높이</form:label>
					<span class="icon-glyph glyph-emark-dot color-warning"></span>
				</th>
				<td class="col-input">
					<form:input path="longitude" cssClass="m" />
					<form:input path="latitude" cssClass="m" />
					<form:input path="altitude" cssClass="m" />
					<input type="button" id="mapButtion" value="지도에서 찾기" />
					<form:errors path="longitude" cssClass="error" />
					<form:errors path="latitude" cssClass="error" />
					<form:errors path="altitude" cssClass="error" />
				</td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
					heading/pitch/roll
				</th>
				<td class="col-input">
					${dataInfo.heading } / ${dataInfo.pitch } / ${dataInfo.roll }
				</td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
					<form:label path="metainfo">메타정보</form:label>
					<span class="icon-glyph glyph-emark-dot color-warning"></span>
				</th>
				<td class="col-input">
					<form:input path="metainfo" class="xl" />
 					<form:errors path="metainfo" cssClass="error" />
				</td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
	            	<form:label path="status">상태</form:label>
	                <span class="icon-glyph glyph-emark-dot color-warning"></span>
				</th>
				<td class="col-input">
					<select id="status" name="status" class="selectBoxClass">
						<option value="processing">변환중 </option>
						<option value="use">사용중</option>
						<option value="unused">사용중지</option>
					</select>
				</td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
					속성 정보
				</th>
				<td class="col-input">
		<c:if test="${dataInfo.attributeExist eq 'true' }">	
							등록
		</c:if>
		<c:if test="${dataInfo.attributeExist eq 'false' }">
							미등록
		</c:if>
				</td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
					Object 속성 정보
				</th>
				<td class="col-input">
		<c:if test="${dataInfo.objectAttributeExist eq 'true' }">	
							등록
		</c:if>
		<c:if test="${dataInfo.objectAttributeExist eq 'false' }">
							미등록
		</c:if>
				</td>
			</tr>
			<tr>
				<th class="col-label l" scope="row"><form:label path="description"><spring:message code='description'/></form:label></th>
				<td class="col-input"><form:input path="description" cssClass="xl" /></td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
					수정일
				</th>
				<td class="col-input">
					${dataInfo.updateDate }
				</td>
			</tr>
			<tr>
				<th class="col-label" scope="row">
					등록일
				</th>
				<td class="col-input">
					${dataInfo.insertDate }
				</td>
			</tr>
		</table>
		<div class="button-group">
			<div class="center-buttons">
				<input type="submit" value="<spring:message code='modified'/>" onclick="updateDataInfo();"/>
				<a href="/data/list" class="button">목록</a>
			</div>
		</div>
		</form:form>
	</div>
	
</div>
<!-- E: WRAP -->

<script type="text/javascript" src="/js/${lang}/common.js"></script>
<script type="text/javascript" src="/js/${lang}/message.js"></script>
<script type="text/javascript" src="/js/${lang}/map-controll.js"></script>
<script type="text/javascript" src="/js/${lang}/ui-controll.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#mappingType").val("${dataInfo.mappingType}");
		$("#status").val("${dataInfo.status}");
	});
	
	function validate() {
		if ($("#longitude").val() === "") {
			alert("대표 위치(경도)를 입력하여 주십시오.");
			$("#longitude").focus();
			return false;
		}
		if ($("#latitude").val() === "") {
			alert("대표 위치(위도)를 입력하여 주십시오.");
			$("#latitude").focus();
			return false;
		}
		if ($("#altitude").val() === "") {
			alert("대표 위치(높이)를 입력하여 주십시오.");
			$("#altitude").focus();
			return false;
		}
		if ($("#metainfo").val() === "") {
			alert("메타 정보를 입력하여 주십시오.");
			$("#metainfo").focus();
			return false;
		}
	}
	
	// 수정
	var updateDataInfoFlag = true;
	function updateDataInfo() {
		if (validate() == false) {
			return false;
		}
		if(updateDataInfoFlag) {
			updateDataInfoFlag = false;
			var formData = $("#dataInfo").serialize();		
			$.ajax({
				url: "/datas/${dataInfo.dataId}",
				type: "POST",
				headers: {"X-Requested-With": "XMLHttpRequest"},
				data: formData,
				success: function(msg){
					if(msg.statusCode <= 200) {
						alert(JS_MESSAGE["update"]);
						window.location.reload();
					} else {
						alert(JS_MESSAGE[msg.errorCode]);
						console.log("---- " + msg.message);
					}
					updateDataInfoFlag = true;
				},
				error:function(request, status, error){
			        alert(JS_MESSAGE["ajax.error.message"]);
			        updateDataInfoFlag = true;
				}
			});
		} else {
			alert(JS_MESSAGE["button.dobule.click"]);
			return;
		}
	}
	
	// 지도에서 찾기
	$( "#mapButtion" ).on( "click", function() {
		var url = "/map/find-data-point?dataId=${dataInfo.dataId}&referrer=MODIFY";
		var width = 1400;
		var height = 700;
	
		var popupX = (window.screen.width / 2) - (width / 2);
		// 만들 팝업창 좌우 크기의 1/2 만큼 보정값으로 빼주었음
		var popupY= (window.screen.height / 2) - (height / 2);
		
	    var popWin = window.open(url, "","toolbar=no ,width=" + width + " ,height=" + height + ", top=" + popupY + ", left="+popupX
	            + ", directories=no,status=yes,scrollbars=no,menubar=no,location=no");
	    //popWin.document.title = layerName;
	});
</script>
</body>
</html>