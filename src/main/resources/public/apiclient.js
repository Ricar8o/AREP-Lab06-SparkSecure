var apiclient = (function () {
    return{
        getFormData: function ($form){
            var unindexed_array = $form.serializeArray();
            var indexed_array = {};
        
            $.map(unindexed_array, function(n, i){
                indexed_array[n['name']] = n['value'];
            });
        
            return indexed_array;
        },
        postMessage: function (nombre, contenido) {
            jQuery.ajax({
                url: "/api/v1/setMessage",
                type: "POST",
                data: JSON.stringify({
                'user' : nombre,
                'message': contenido
                }),
            });
        },
        login: function(){
            var $form = $('#loginForm');
            var datos = this.getFormData($form);
			$.ajax({
		        url: '/login',
		        type: 'POST',
		        contentType: 'application/json',
		        data: JSON.stringify(datos),
		        success: function(data){
		        	alert("You are now logged in")
		        },
		        error: function(x){
		        	console.error("encountered a problem: ", x);
		        }
		    });
			return false;

        },
        
        logout: function(){
			$.ajax({
		        url: '/logout',
		        type: 'POST',
		        success: function(data){
		        	alert("You are now logged out")
		        },
		        error: function(x){
		        	console.error("encountered a problem: ", x);
		        }
		    });
			return false;

		}
    };
})();