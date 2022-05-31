let targetId;


$(document).ready(function () {

    getPosts();

    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });
    $('#close').on('click', function () {
        $('#container').removeClass('active');
    })

    $('.nav div.nav-see').on('click', function () {
        $('div.nav-see').addClass('active');
        $('div.nav-search').removeClass('active');

        $('#see-area').show();
        $('#search-area').hide();
    })


    $('#see-area').show();
    $('#search-area').hide();

    if ($('#admin').length === 1) {
        showProduct(true);
    } else {
        showProduct();
    }
})

function getPosts(){
    $.ajax({
        type: "GET",
        url: "/api/posts",
        success: function (response){
            for(let i=0; i<response.length; i++){
                let post = response[i];
                let tempHtml = addHTML(post);
                $('#product-container').append(tempHtml);

            }
        }
    })
}

function isValidContents(contents) {
    if (contents == '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (contents.trim().length > 140) {
        alert('공백 포함 140자 이하로 입력해주세요');
        return false;
    }
    return true;
}

function addHTML(post) {
    return `<div class="search-itemDto">
                <div class="search-itemDto-center" onclick='location.replace("/post?id=${post.id}")'>
                    <div>${post.title}</div>
                    <div class="price">
                        작성자: ${post.username}
                    </div>
                </div>
                </div>
            </div>`
}

function showProduct(isAdmin = false) {
    // 1. GET /api/products 요청
    // 2. #product-container(관심상품 목록), #search-result-box(검색결과 목록) 비우기
    // 3. for 문 마다 addProductItem 함수 실행시키고 HTML 만들어서 #product-container 에 붙이기
    $.ajax({
        type: 'GET',
        url: isAdmin ? '/api/admin/products' : '/api/products',
        success: function (response) {
            $('#product-container').empty();
            $('#search-result-box').empty();
            for (let i = 0; i < response.length; i++) {
                let product = response[i];
                let tempHtml = addProductItem(product);
                $('#product-container').append(tempHtml);
            }
        }
    })
}

function addProductItem(product) {
    return `<div class="product-card" onclick="window.location.href='${product.link}'">
                <div class="card-header">
                    <img src="${product.image}"
                         alt="">
                </div>
                <div class="card-body">
                    <div class="title">
                        ${product.title}
                    </div>
                    <div class="lprice">
                        <span>${numberWithCommas(product.lprice)}</span>원
                    </div>
                    <div class="isgood ${product.lprice > product.myprice ? 'none' : ''}">
                        최저가
                    </div>
                </div>
            </div>`;
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function execSearch() {
    let query = $('#query').val();
    if (query == '') {
        alert('검색어를 입력해주세요');
        $('#query').focus();
        return;
    }
    $.ajax({
        type: 'GET',
        url: `/api/search?query=${query}`,
        success: function (response) {
            $('#search-result-box').empty();
            for (let i = 0; i < response.length; i++) {
                let itemDto = response[i];
                let tempHtml = addHTML(itemDto);
                $('#search-result-box').append(tempHtml);
            }
        }
    })
}


function addProduct(itemDto) {
    $.ajax({
        type: "POST",
        url: '/api/products',
        contentType: "application/json",
        data: JSON.stringify(itemDto),
        success: function (response) {
            $('#container').addClass('active');
            targetId = response.id;
        }
    })
}

function setMyprice() {
    let myprice = $('#myprice').val();
    if (myprice == '') {
        alert('올바른 가격을 입력해주세요');
        return;
    }
    $.ajax({
        type: "PUT",
        url: `/api/products/${targetId}`,
        contentType: "application/json",
        data: JSON.stringify({myprice: myprice}),
        success: function (response) {
            $('#container').removeClass('active');
            alert('성공적으로 등록되었습니다.');
            window.location.reload();
        }
    })
}