import React,{PureComponent} from 'react';
import PropTypes from 'prop-types';
import setClassnames from 'classnames';

/**
 * Renders a styled button to the page.
 * */

export default class Button extends PureComponent {

  static propTypes = {
    extraClasses: PropTypes.string,
    className: PropTypes.string,
    classArray: PropTypes.array,
    fullWidth: PropTypes.bool,
    id: PropTypes.string,
    isDisabled: PropTypes.bool,
    label: PropTypes.string.isRequired,
    onClick: PropTypes.func,
  };

  static defaultProps = {
    label: 'Button Default',
    size: 'md',
    type: 'primary'
  };

  render() {
    const {
      extraClasses,
      id,
      isDisabled,
      label,
      onClick,
      classString,
      classArray,
      className,
      fullWidth
    } = this.props;

    // const ButtonImpl = extraClassesSemicolon ? ButtonStyleBase.extend`${extraClassesSemicolon}`: ButtonStyleBase;
 
    return (
      <button
        id={id}
        type="button"
        className={setClassnames(
          'btn',
          {
            [`${extraClasses}`]: !!extraClasses,
            [`${className}`]: !!className,
            'disabled-button': isDisabled,
            'full-width': fullWidth,
          })}
        onClick={onClick}
        disabled={isDisabled}>
        {label}
      </button>
    );
  }
}
