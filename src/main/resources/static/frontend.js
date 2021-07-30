function postRandomBook() {
    $.ajax({
        url: "/books",
        method: "POST",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify({
            title: "Random Title",
            author: "Random Author",
            year: 1900 + Math.floor(Math.random() * 100)
        }),
        dataType: "json"
    })
    .done(data => console.log(data))
    .fail(error => console.log(error.responseJSON));
}

window.onload = function() {
    for (let i = 0; i < 10; ++i) {
        postRandomBook();
    }
}
