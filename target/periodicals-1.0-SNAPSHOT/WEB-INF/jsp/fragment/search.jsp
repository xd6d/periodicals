<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="search">
    <form action="/" name="search-form">
        <label>
            <input type="search" name="search" placeholder="Search for a publication here" value="${param.search}"
                   id="search">
        </label>
        <label>
            <input type="submit" value="Search">
        </label>
        <label>
            <input type="submit" value="Reset" onclick="document.getElementById('search').value=''">
        </label>
        <input type="hidden" name="sort" value="${param.sort}">
        <input type="hidden" name="reversed" value="${param.reversed}">
        <input type="hidden" name="topic" value="${param.topic}">
    </form>
</div>
