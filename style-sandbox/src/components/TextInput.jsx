import React,{PureComponent} from 'react';
import PropTypes from 'prop-types';
import setClassnames from 'classnames';

/**
 * Renders a styled button to the page.
 * */

export default class TextInput extends PureComponent {

  static propTypes = {
    extraClasses: PropTypes.string,
    className: PropTypes.string,
    classArray: PropTypes.array,
    fullWidth: PropTypes.bool,
    id: PropTypes.string,
    isDisabled: PropTypes.bool,
    label: PropTypes.string.isRequired,
    placeholder: PropTypes.string,
    onClick: PropTypes.func,
  };

  static defaultProps = {
    label: 'Input Default',
    type: 'primary'
  };

  render() {
    const {
      extraClasses,
      id,
      isDisabled,
      label,
      onClick,
      className,
      fullWidth,
     placeholder
    } = this.props;

    // const InputImpl = extraClassesSemicolon ? InputStyleBase.extend`${extraClassesSemicolon}`: InputStyleBase;
 //<input type='text' placeholder='Username' class='input-line full-width'></input>
    return (
      <input
        id={id}
        placeholder={placeholder}
        type="text"
        className={setClassnames(
          {
            [`${extraClasses}`]: !!extraClasses,
            [`${className}`]: !!className,
            'disabled-button': isDisabled,
            'full-width': fullWidth,
          })}
        //onClick={onClick}
       // disabled={isDisabled}
        >
      </input>
    );
  }
}
