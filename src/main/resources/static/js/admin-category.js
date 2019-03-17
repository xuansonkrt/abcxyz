$(document).ready(function() {

    var dataCategory = {};


    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $('#preview-product-img').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }






    $("#new-category").on("click", function () {
        dataCategory = {};
        $('#input-category-name').val("");
        $('#input-category-desc').val("");


    });


    $(".edit-category").on("click", function () {
        var pdInfo = $(this).data("category");
        console.log(pdInfo);
        NProgress.start();
        axios.get("/api/category/detail/" + pdInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                console.log("category: ",res.data.data);
                dataCategory.id = res.data.data.id;
                $("#input-category-name").val(res.data.data.name);
                $("#input-category-desc").val(res.data.data.shortDesc);


            }else {
                console.log("ahihi");
            }
        }, function(err){
            NProgress.done();
        })
    });



    $(".btn-save-category").on("click", function () {
        if($("#input-product-name").val() === "" || $("#input-product-desc").val() === "" ) {
            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }


        dataCategory.name = $('#input-category-name').val();
        dataCategory.shortDesc = $('#input-category-desc').val();

        NProgress.start();
        console.log(dataCategory.id);
        var linkPost = "/api/category/create";
        if(dataCategory.id) {
            linkPost = "/api/category/update/" + dataCategory.id;
        }

        axios.post(linkPost, dataCategory).then(function(res){
            NProgress.done();
            if(res.data.success) {
                swal(
                    'Good job!',
                    res.data.message,
                    'success'
                ).then(function() {
                    location.reload();
                });
            } else {
                swal(
                    'Error',
                    res.data.message,
                    'error'
                );
            }
        }, function(err){
            NProgress.done();
            swal(
                'Error',
                'Some error when saving product',
                'error'
            );
        })
    });



});