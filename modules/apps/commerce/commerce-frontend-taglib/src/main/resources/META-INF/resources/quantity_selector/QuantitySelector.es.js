/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';
import QuantityControls, {UPDATE_AFTER} from './util/index';

import template from './QuantitySelector.soy';

class QuantitySelector extends Component {
	attached() {
		this.controls = new QuantityControls({...this});
		this.inputDebounceTimeout = null;

		if (!this.quantity) {
			this.quantity = this.allowedQuantities
				? this.allowedQuantities[0]
				: this.controls.getLowerBound(this.minQuantity);

			this._updateQuantity(this.quantity);
		}
	}

	syncQuantity() {
		this.checkButtonsAvailability(this.quantity);
	}

	prepareStateForRender(state) {
		this.checkButtonsAvailability(state.quantity);
	}

	_handleSelectOption(e) {
		const quantity = parseInt(e.target.value, 10);

		return this.emit('updateQuantity', quantity);
	}

	checkButtonsAvailability(quantity) {
		this._prevAvailable = this._isPrevButtonAvailable(quantity);
		this._nextAvailable = this._isNextButtonAvailable(quantity);
	}

	_isPrevButtonAvailable(quantity) {
		const tempValue = this.multipleQuantity
			? quantity - this.multipleQuantity
			: quantity - 1;

		return tempValue >= this.minQuantity;
	}

	_isNextButtonAvailable(quantity) {
		const tempValue = this.multipleQuantity
			? quantity + this.multipleQuantity
			: quantity + 1;

		return tempValue <= this.maxQuantity;
	}

	_handlePrevQuantityButtonPressed(e) {
		e.preventDefault();
		if (!this._prevAvailable) {
			this.showError = true;

			return false;
		}

		let quantity = this.quantity;

		if (this.multipleQuantity) {
			quantity -= this.multipleQuantity;
		}
		else {
			quantity -= 1;
		}

		if (quantity < this.minQuantity) {
			this.inputError = 'MaxAvailableReached';

			return false;
		}

		return this._updateQuantity(quantity);
	}

	_handleNextQuantityButtonPressed(e) {
		e.preventDefault();
		if (!this._nextAvailable) {
			this.showError = true;

			return false;
		}

		let quantity = this.quantity;

		if (this.multipleQuantity) {
			quantity += this.multipleQuantity;
		}
		else {
			quantity += 1;
		}

		if (quantity > this.maxQuantity) {
			this.inputError = 'MaxAvailableReached';

			return false;
		}

		return this._updateQuantity(quantity);
	}

	_handleArrowKeys(e) {
		if (e.keyCode == 38) {
			return this._handleNextQuantityButtonPressed(e);
		}
		if (e.keyCode == 40) {
			return this._handlePrevQuantityButtonPressed(e);
		}

		return e;
	}

	_handleInputKeyUp(e) {
		clearTimeout(this.inputDebounceTimeout);

		if (!e.target.value) {
			return null;
		}

		this.inputDebounceTimeout = setTimeout(() => {
			const controlledQuantity = this.controls.getLowerBound(
				parseInt(e.target.value, 10)
			);

			e.target.value = controlledQuantity;

			this._updateQuantity(controlledQuantity);
		}, UPDATE_AFTER);
	}

	_handleFormSubmit(e) {
		e.preventDefault();
		this.showError = true;

		return this.emit('submitQuantity', this.quantity);
	}

	_updateQuantity(quantity) {
		this.showError = false;
		this.quantity = quantity;

		return this.emit('updateQuantity', quantity);
	}
}

QuantitySelector.STATE = {
	_nextAvailable: Config.bool().value(true),
	_prevAvailable: Config.bool().value(true),
	allowedQuantities: Config.array(Config.number()),
	disabled: Config.bool().value(false),
	inputError: Config.string(),
	maxQuantity: Config.number().value(99999999),
	minQuantity: Config.number().value(1),
	multipleQuantity: Config.number(),
	quantity: Config.number(),
	showError: Config.bool().value(false)
};

Soy.register(QuantitySelector, template);

export {QuantitySelector};
export default QuantitySelector;