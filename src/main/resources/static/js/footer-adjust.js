$(() => {
    let documentHeight = $(document).height();
    let windowHeight = $(window).height();

    if (documentHeight === windowHeight) {
        $("footer").addClass("fixed-bottom");
    }
});