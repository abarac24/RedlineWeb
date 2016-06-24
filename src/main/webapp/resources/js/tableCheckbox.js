$('table').tableCheckbox({

// The class that will be applied to selected rows.
selectedRowClass: 'warning',

// The selector used to find the checkboxes on the table. 
// You may customize this in order to match your table layout
//  if it differs from the assumed one.
checkboxSelector: 'td:first-of-type input[type="checkbox"],th:first-of-type input[type="checkbox"]',

// A callback that is used to determine wether a checkbox is selected or not.
isChecked: function($checkbox) {
  return $checkbox.is(':checked');
}

});