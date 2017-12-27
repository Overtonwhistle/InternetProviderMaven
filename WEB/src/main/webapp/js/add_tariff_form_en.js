function validateForm() {
	var result = true;
	var 
	FILL_FIELD = " *fill this field", 
	BAD_COST = " *incorrect monthly cost", 
	BAD_LIMIT = " *incorrect monthly limit", 
	BAD_OVERLOAD = " *incorrect overload cost";
	
	var errTitle = document.getElementById("err-title"); 
	var errCost = document.getElementById("err-cost");
	var errLimit = document.getElementById("err-limit");
	var errOverload = document.getElementById("err-overload");

	errTitle.innerHTML = "";
	errCost.innerHTML = "";
	errLimit.innerHTML = "";
	errOverload.innerHTML = "";
	
	var title = document.AddTariff.new_title.value;
	var cost = document.AddTariff.new_monthly_cost.value;
	var limit = document.AddTariff.new_limit.value;
	var unlim = document.AddTariff.new_unlim.value;
	var overload = document.AddTariff.new_overload_cost.value;
	

	if (!title) {
		errTitle.innerHTML = FILL_FIELD;
		setInvalid(document.AddTariff.new_title);
		result = false;
	} else {
		setValid(document.AddTariff.new_title);
	}



	if (!cost) {
		errCost.innerHTML = FILL_FIELD;
		setInvalid(document.AddTariff.new_monthly_cost);
		result = false;
	} else if ((cost.search(/^[0-9]+\.*[0-9]*$/) !== 0||(cost==0))) {
		errCost.innerHTML = BAD_COST;
		setInvalid(document.AddTariff.new_monthly_cost);
		result = false;
	} else {
		setValid(document.AddTariff.new_monthly_cost);
	}

	
	if (unlim=="no") {
	if (!limit) {
		errLimit.innerHTML = FILL_FIELD;
		setInvalid(document.AddTariff.new_limit);
		result = false;
	} else if ((limit.search(/^[0-9]*$/) !== 0) ||(limit<=0)) {
		errLimit.innerHTML = BAD_LIMIT;
		setInvalid(document.AddTariff.new_limit);
		result = false;
	} else {
		setValid(document.AddTariff.new_limit);
	}
	}
	else {
		setValid(document.AddTariff.new_limit);
	}
	
	if (unlim=="no") {
		if (!overload) {
			errOverload.innerHTML = FILL_FIELD;
			setInvalid(document.AddTariff.new_overload_cost);
			result = false;
		} else if ((overload.search(/^[0-9]+\.*[0-9]*$/) !== 0||(overload==0))) {
			errOverload.innerHTML = BAD_OVERLOAD;
			setInvalid(document.AddTariff.new_overload_cost);
			result = false;
		} else {
			setValid(document.AddTariff.new_overload_cost);
		}
		}
		else {
			setValid(document.AddTariff.new_overload_cost);
		}
	
	

	return result;
}

function setInvalid(node) {
	node
			.setAttribute(
					'style',
					'background: #fff url(images/invalid.png) no-repeat 98% center;  box-shadow: 0 0 5px #d45252; border-color: #b03535; ');
}

function setValid(node) {
	node
			.setAttribute(
					'style',
					'background: #fff url(images/valid.png) no-repeat 98% center; box-shadow: 0 0 5px #5cd053; border-color: #28921f;');
}


