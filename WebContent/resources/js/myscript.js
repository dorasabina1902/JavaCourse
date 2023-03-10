function togglePassword() {
	var newType = $element.prop('type') == 'password' ? 'text' : 'password';
	$element.prop('type', newType);
}

// Change active navbar header color
let current_url = document.location;
document.querySelectorAll("ul.navbar-nav li.nav-item a.nav-link").forEach(function(e) {
	if (e.href == current_url) {
		e.classList += " active";
		console.log(e.href);
		console.log(document.location);
	}
});



function showImageChoice(myInputFile) {

	var myDivId = "#" + $(myInputFile).attr("id") + "View";
	var myErrorDiv = myDivId + " .error-message";

	var reader = new FileReader();
	var files = myInputFile.files[0];
	reader.readAsDataURL(files);
	reader.onloadend = function(e) {

		$(myDivId).css('display', 'block');

		result = e.target.result;
		console.log(e.target);

		var fileName = myInputFile.value.substring(12);

		if (fileName.length > 12) {
			fileName = fileName.substring(0, 4).concat("...", fileName.substring(fileName.length - 4, fileName.length)); console.log(fileName);
		} else {
			fileName = fileName;
		}

		$(myInputFile).closest("div").find("span").html(fileName);

		if (result.includes("image")) {

			$(myErrorDiv).css('display', 'none').empty();
			$(myDivId + " .show-image").attr("src", result);

		} else {

			errorMessage = "Invalid image format!<br/> A default image has been generated for you.";
			$(myErrorDiv).css('display', 'block').html(errorMessage);


			imgDir = myDivId.substring(3) + ".png";
			$(myDivId + " .show-image").attr("src", "../../resources/images/default/" + imgDir);

		}
	}
}

; (function(window) {

	'use strict';

	var document = window.document;

	function textSearcher(query_selector, input_field, search_count_output, result_class) {
		this._init(query_selector, input_field, search_count_output, result_class);

		return {
			_init: this._init.bind(this),
			_search: this._search.bind(this),
			_destroy: this._destroy.bind(this),
		}
	}

	textSearcher.prototype = {
		_init: function(query_selector, input_field, search_count_output, result_class) {
			var document_nodes = document.querySelectorAll(query_selector);
			this.searchable_nodes = [];
			this.search_instances = [];

			for (var node_index = 0; node_index < document_nodes.length; node_index++) {
				var node = document_nodes[node_index];

				if (node.offsetParent !== null && node.offsetHeight > 0 && node.childNodes.length && node.innerText.length) {
					this.searchable_nodes.push(node);
				}
			}

			this.searchable_nodes_length = this.searchable_nodes.length;

			if (input_field && (input_field = document.querySelectorAll(input_field)[0])) {
				this.input_field = input_field;

				this.input_field.addEventListener("keyup", this.searchInputValue.bind(this));
			}

			if (search_count_output && (search_count_output = document.querySelectorAll(search_count_output)[0])) {
				this.search_count_output = search_count_output;
			}

			this.result_class = result_class || "js-textSearcher-highlight";

			return null;
		},

		_search: function(search_value) {

			if (typeof search_value == "undefined") {
				if (this.input_field) {
					search_value = this.input_field.value;
				} else {
					console.error("You can only call this method without a value if an input field is bound");
					return false;
				}
			}

			var search_value_length = search_value.length,
				search_regex = new RegExp(search_value.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&"), "gi"),
				node_index = 0;

			this.search_count = 0;

			var instance_index = 0;

			while (instance_index < this.search_instances.length) {
				this.search_instances[instance_index].revert();
				instance_index++;
			}

			this.search_instances = [];

			if (search_value_length) {
				while (node_index < this.searchable_nodes_length) {
					var node = this.searchable_nodes[node_index];

					var instance = findAndReplaceDOMText(node, {
						find: search_regex,
						replace: function(portion, match) {
							var el = document.createElement('span');
							el.className = this.result_class;
							el.innerHTML = portion.text;

							return el;
						}.bind(this)
					});

					this.search_count += instance.reverts.length;

					this.search_instances.push(instance);

					node_index++;
				}
			}

			if (this.search_count_output) {
				this.search_count_output.textContent = this.search_count;
			}
		},

		_destroy: function() {
			if (this.input_field) {
				this.input_field.removeEventListener("keyup", this.searchInputValue);
			}
		},

		searchInputValue(event) {
			this._search(event.target.value);
		}
	}

	window.textSearcher = textSearcher;

})(window);


$(document).ready(function() {

	$("#btnSearchInputNavbar").unbind('click');

	$("#btnSearchInputNavbar").bind('click', function(e) {
		var searcher = new textSearcher(".container", ".search-input-navbar", ".search-count-navbar");
		searcher._search();
	});


	/* Password Field's Visibility */

	$('.pwdStatClass').click(function() {

		$('#pwdIconStudentCr').toggleClass("fa-eye fa-eye-slash");
		$('#pwdIconStudentUp').toggleClass("fa-eye fa-eye-slash");
		$('#pwdIconTeacherCr').toggleClass("fa-eye fa-eye-slash");
		$('#pwdIconTeacherUp').toggleClass("fa-eye fa-eye-slash");
		$('#pwdIconAdminCr').toggleClass("fa-eye fa-eye-slash");
		$('#pwdIconAdminUp').toggleClass("fa-eye fa-eye-slash");

		var studentPass = $('.stdPwdField');
		var teacherPass = $('.tecPwdField');
		var adminPass = $('.admPwdField');

		if (studentPass.attr("type") == 'password') {
			studentPass.attr("type", "text");

		} else if (teacherPass.attr("type") == 'password') {
			teacherPass.attr("type", "text");

		} else if (adminPass.attr("type") == 'password') {
			adminPass.attr("type", "text");

		} else {
			studentPass.attr("type", "password");
			teacherPass.attr("type", "password");
			adminPass.attr("type", "password");
		}
	});

	$('#searchInputNavbar').bind('focus blur', function(event) {
		if (event.type === 'focus') {
			$(this).css({
				'transiton': '0.5s transition all ease',
				'border-color': '#3333cc'
			});
		}
		else if (event.type === 'blur') {
			$(this).css({
				'transiton': '0.5s transition all ease',
				'border-color': '#fff'
			});
		}
	});


	$('.homepage-block .member-card .member-thumb img').on('mouseover mouseout', function(event) {
		if (event.type === 'mouseover') {
			$(this).css({
				'border': '0',
				'transiton': '0.8s transition all ease',
				'transform': 'scale(1.1)'
			});
		}
		else if (event.type === 'mouseout') {
			$(this).css({
				'border': '0',
				'transiton': '0.8s transition all ease',
				'transform': 'scale(1.0)'
			});
		}
	});

	$("li.first-one").click(function() {
		$("li.second-one").removeClass("active");
		$(this).addClass("active");
	});

	$("li.second-one").click(function() {
		$("li.first-one").removeClass("active");
		$(this).addClass("active");
	});


	$('.first-one .dropdown-item.add-me').bind('mouseover mouseout', function(event) {
		$("li.second-one").removeClass("active");


		if (event.type == 'mouseover') {
			$(this).addClass('dropdown-item-highlight');
			$(this).closest("div").parent("li").addClass("active");
		}
		else if (event.type == 'mouseout') {
			$(this).removeClass('dropdown-item-highlight');
		} else {
			$(this).closest("div").parent("li").removeClass("active");
			$(this).removeClass('dropdown-item-highlight');
		}
	});

	$('.second-one .dropdown-item.add-me').bind('mouseover mouseout', function(event) {
		$("li.first-one").removeClass("active");


		if (event.type == 'mouseover') {
			$(this).addClass('dropdown-item-highlight');
			$(this).closest("div").parent("li").addClass("active");
		}
		else if (event.type == 'mouseout') {
			$(this).removeClass('dropdown-item-highlight');
		} else {
			$(this).closest("div").parent("li").removeClass("active");
			$(this).removeClass('dropdown-item-highlight');
		}
	});

	$('.ribbon').on('click', function() {
		var self = $(this),
			newone = self.clone(true);

		self.before(newone);
		$("." + self.attr("class") + ":last").remove();
	});

});




