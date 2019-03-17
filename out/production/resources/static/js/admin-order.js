$(document).ready(function() {

    var dataOrder = {};


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


    $(".edit-order").on("click", function () {
        var pdInfo = $(this).data("order");
        console.log(pdInfo);
        NProgress.start();
        axios.get("/api/order/detail/" + pdInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                console.log("category: ",res.data.data);
                dataOrder.id = res.data.data.id;
                $("#input-customer-name").val(res.data.data.customerName);
                $("#input-phone").val(res.data.data.phoneNumber);
                $("#input-email").val(res.data.data.email);
                $("#input-address").val(res.data.data.address);


            }else {
                console.log("ahihi");
            }
        }, function(err){
            NProgress.done();
        })
    });



    $(".btn-save-order").on("click", function () {
        if($("#input-customer-name").val() === "" || $("#input-phone").val() === "" ) {
            swal(
                'Error',
                'You need to fill all values',
                'error'
            );
            return;
        }


        dataOrder.customerName = $('#input-customer-name').val();
        dataOrder.email = $('#input-email').val();
        dataOrder.address = $('#input-address').val();
        dataOrder.phoneNumber = $('#input-phone').val();

        NProgress.start();
        var linkPost = "/api/order/create";
        if(dataOrder.id) {
            linkPost = "/api/order/update/" + dataOrder.id;
        }

        axios.post(linkPost, dataOrder).then(function(res){
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

    $(".edit-order").on("click", function () {
        var pdInfo = $(this).data("order");
        console.log(pdInfo);
        NProgress.start();
        axios.get("/api/order/detail-order/" + pdInfo).then(function(res){
            NProgress.done();
            if(res.data.success) {
                console.log("category: ",res.data.data);
                dataOrder.id = res.data.data.id;
                var length= res.data.data.

                $("#input-customer-name").val(res.data.data.customerName);
                $("#input-phone").val(res.data.data.phoneNumber);
                $("#input-email").val(res.data.data.email);
                $("#input-address").val(res.data.data.address);


            }else {
                console.log("ahihi");
            }
        }, function(err){
            NProgress.done();
        })
    });

});