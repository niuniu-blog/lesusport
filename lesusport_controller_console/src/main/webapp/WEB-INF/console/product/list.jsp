<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>babasport-list</title>
    <script type="text/javascript">
        //上架
        function showProduct(name, brandId, isShow, pageNo) {
            //请至少选择一个
            var size = $("input[name='ids']:checked").size();
            if (size == 0) {
                alert("请至少选择一个");
                return;
            }
            //你确定删除吗
            if (!confirm("你确定上架吗")) {
                return;
            }
            //提交 Form表单
            $("#jvForm").attr("action", "/product/showProduct?name=" + name + "&brandId=" + brandId + "&isShow=" + isShow + "&pageNo=" + pageNo);
            $("#jvForm").attr("method", "post");
            $("#jvForm").submit();
        }

        function isHide(name, brandId, isShow, pageNo) {
            //请至少选择一个
            var size = $("input[name='ids']:checked").size();
            if (size == 0) {
                alert("请至少选择一个");
                return;
            }
            //你确定删除吗
            if (!confirm("你确定下架吗")) {
                return;
            }
            //提交 Form表单
            $("#jvForm").attr("action", "/product/downProduct?name=" + name + "&brandId=" + brandId + "&isShow=" + isShow + "&pageNo=" + pageNo);
            $("#jvForm").attr("method", "post");
            $("#jvForm").submit();
        }

        //删除数据
        function optDelete(name, brandId, isShow, pageNo) {
            var flag;
            var size = $("input[name=ids]:checked").size();
            if (size == 0) {
                alert("请至少选择一条数据");
                return;
            }
            var $input = $("input[name=ids]:checked");
            $.each($input, function (index, val) {
                var id = "#s" + val.value;
                if ($(id).val() == "true") {
                    flag = true;
                }
            });
            if (flag) {
                alert("请先下架商品再删除！");
                return;
            }
            if (!confirm("确认删除吗？")) {
                return;
            }
            $("#jvForm").attr("method", "post");
            $("#jvForm").attr("action", "/product/removeProducts?name=" + name + "&brandId=" + brandId + "&isShow=" + isShow + "&pageNo=" + pageNo);
            $("#jvForm").submit();
        }

        //删除单条数据
        function removeProduct(id, name, brandId, isShow, pageNo) {
            var sid = "#s" + id;
            if ($(id).val() == true) {
                alert("请先下架商品再删除！");
                return;
            }
            if (!confirm("确认删除吗？")) {
                return;
            }
            window.location.href = "/product/removeProduct/" + id + "?name=" + name + "&brandId=" + brandId + "&isShow=" + isShow + "&pageNo=" + pageNo;
        }
    </script>
</head>
<body>
<div class="box-positon">
    <div class="rpos">当前位置: 商品管理 - 列表</div>
    <form class="ropt">
        <input class="add" type="button" value="添加" onclick="window.location.href='/product/toAdd'"/>
    </form>
    <div class="clear"></div>
</div>
<div class="body-box">
    <form action="/product/list.do" method="post" style="padding-top:5px;">
        名称: <input type="text" name="name" value="${name}"/>
        <select name="brandId">
            <option value="">请选择品牌</option>
            <c:forEach items="${brands}" var="brand">
                <option value="${brand.id}"
                        <c:if test="${brand.id == brandId}">selected="selected"</c:if>>${brand.name}</option>
            </c:forEach>
        </select>
        <select name="isShow">
            <option value="1" <c:if test="${isShow}">selected="selected"</c:if>>上架</option>
            <option value="0" <c:if test="${!isShow}">selected="selected"</c:if>>下架</option>
        </select>
        <input type="submit" class="query" value="查询"/>
    </form>
    <form id="jvForm">
        <table cellspacing="1" cellpadding="0" width="100%" border="0" class="pn-ltable">
            <thead class="pn-lthead">
            <tr>
                <th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
                <th>商品编号</th>
                <th>商品名称</th>
                <th>图片</th>
                <th width="4%">新品</th>
                <th width="4%">热卖</th>
                <th width="4%">推荐</th>
                <th width="4%">上下架</th>
                <th width="12%">操作选项</th>
            </tr>
            </thead>
            <tbody class="pn-ltbody">
            <c:forEach items="${pagination.list}" var="product">
                <tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
                    <td><input type="checkbox" name="ids" value="${product.id}"/></td>
                    <td>${product.id}</td>
                    <td align="center">${product.name}</td>
                    <td align="center"><img width="50" height="50" src="${product.images}"/></td>
                    <td align="center">
                        <c:if test="${product.isNew}">是</c:if>
                        <c:if test="${!product.isNew}">否</c:if>
                    </td>
                    <td align="center">
                        <c:if test="${product.isHot}">是</c:if>
                        <c:if test="${!product.isHot}">否</c:if>
                    </td>
                    <td align="center">
                        <c:if test="${product.isCommend}">是</c:if>
                        <c:if test="${!product.isCommend}">否</c:if>
                    </td>
                    <td align="center">
                        <c:if test="${product.isShow}">上架</c:if>
                        <c:if test="${!product.isShow}">下架</c:if>
                        <input type="hidden" id="s${product.id}" value="${product.isShow}"/>
                    </td>
                    <td align="center">
                        <a href="#" class="pn-opt">查看</a> | <a href="#"
                                                               onclick="window.location.href='/product/updateProductUi/${product.id}'"
                                                               class="pn-opt">修改</a> | <a href="#"
                                                                                          onclick="removeProduct('${product.id}','${name}','${brandId}','${isShow}','${pagination.pageNo}')"
                                                                                          class="pn-opt">删除</a> |
                        <a href="/sku/list/${product.id}" class="pn-opt">库存</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="page pb15">
	<span class="r inb_a page_b">
        <c:forEach items="${pagination.pageView}" var="page">
            ${page}
        </c:forEach>
	</span>
        </div>
        <div style="margin-top:15px;"><input class="del-button" type="button" value="删除"
                                             onclick="optDelete('${name}','${brandId}','${isShow}','${pagination.pageNo}');"/><input
                class="add" type="button" value="上架"
                onclick="showProduct('${name}','${brandId}','${isShow}','${pagination.pageNo}');"/><input
                class="del-button" type="button"
                value="下架" onclick="isHide('${name}','${brandId}','${isShow}','${pagination.pageNo}');"/></div>
    </form>
</div>
</body>
</html>