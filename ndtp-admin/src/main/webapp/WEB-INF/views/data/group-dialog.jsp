<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="dataGroupDialog" title="데이터 그룹 정보">
	<table class="inner-table scope-row">
		<col class="col-label" />
		<col class="col-data" />
		<tr>
			<th class="col-label" scope="row">데이터 그룹명</th>
			<td id="dataGroupNameInfo" class="col-data"></td>
		</tr>
		<tr>
			<th class="col-label" scope="row">공유타입</th>
			<td id="sharingInfo" class="col-data"></td>
		</tr>
		<tr>
			<th class="col-label" scope="row">사용유무</th>
			<td id="availableInfo" class="col-data"></td>
		</tr>
		<tr>
			<th class="col-label" scope="row"><spring:message code='description'/></th>
			<td id="descriptionInfo" class="col-data"></td>
		</tr>
	</table>
</div>