import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App.jsx';
import registerServiceWorker from './registerServiceWorker';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './component-styles/App.css'
import './component-styles/color-pallete.css'

import 'styled-components'




ReactDOM.render(<App /> , document.getElementById('root'));
registerServiceWorker();