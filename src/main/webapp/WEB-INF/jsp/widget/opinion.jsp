<%@ page language='java' contentType='text/html; charset=UTF-8' pageEncoding='UTF-8'%>

<!-- 悬浮 意见反馈 -->
   <a href="javascript:void(0);" class="opinion-right" id="opinionHrefId"><img src="${EXTERNAL_URL }images/opinion-right.png"></a>
    <script type="text/javascript">
    	$(function(){
    	     $("#opinionHrefId").click(function(){
        	    location.href="${EXTERNAL_URL }opinion-feedback.html?return_mapping="+encodeURIComponent(encodeURIComponent("original-works/index.html"));
        	}); 
    	});
    </script>