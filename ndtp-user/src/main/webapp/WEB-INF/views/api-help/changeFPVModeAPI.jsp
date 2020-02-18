<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="api16" class="apihelptoggle" style="display: none;">
	<div class="menu_tab">
		<ul>
			<li class="fst active"><a href="#;" onclick="tab_menu(0);">Run</a></li>
			<li><a href="#;" onclick="tab_menu(1);">Code</a></li>
		</ul>
	</div>
	<div class="menu_tab00 mTs" id="panels">
	<h2>changeFPVModeAPI</h2>

		<p>카메라를 1인칭, 3인칭 모드로 변경해주는 API입니다.</p>
		<h4>파라미터</h4>
		<table>
		<tr><th>name</th><th>type</th><th>description</th></tr>
		<tr><td>managerFactoryInstance</td><td>ManagerFactory</td><td>mago3D 시작 부분</td></tr>
		<tr><td>flag</td><td>Boolean</td><td>true: 활성화, false: 비활성화</td></tr>
		</table></br>
		<h4>실행</h4>
		<div class="paramContainer">
		<label><input type="radio" name="api16-p1" value="true" />활성</label>
		<label><input type="radio" name="api16-p1" value="false" checked />비활성</label></div></br> 
		<input type="button" value="Run" class="popupBtn" onclick="changeFPVMode()">
	</div>
	<div class="menu_tab01 mTs" id="panels" style="display: none;">
		</div>
</div>
<script>
   var changeFPVMode = function() {

      var p1 = $("input[name=api16-p1]:checked").val();

      var isFalseBoolean = (p1 === 'true');

      changeFPVModeAPI(MAGO3D_INSTANCE2, isFalseBoolean);
   }
</script>