
    function toggleMaxInput(field) {
        const select = document.getElementById(field + 'Comparison');
        const maxInput = document.getElementById(field + 'Max');
        const mainInput = document.getElementById(field);

        if (select.value === 'between') {
            maxInput.style.display = 'inline-block';

            switch (field) {
                case 'price':
                    mainInput.placeholder = 'Start Price';
                    maxInput.placeholder = 'Max Price';
                    break;
                case 'mileage':
                    mainInput.placeholder = 'Min Mileage';
                    maxInput.placeholder = 'Max Mileage';
                    break;
                case 'year':
                    mainInput.placeholder = 'From Year';
                    maxInput.placeholder = 'To Year';
                    break;
            }
        } else {
            maxInput.style.display = 'none';

            switch (field) {
                case 'price':
                    mainInput.placeholder = 'Suggested price';
                    break;
                case 'mileage':
                    mainInput.placeholder = 'Mileage in km';
                    break;
                case 'year':
                    mainInput.placeholder = 'Manufacturing year';
                    break;
            }
        }
    }
        ['price', 'mileage', 'year'].forEach(field => toggleMaxInput(field));


