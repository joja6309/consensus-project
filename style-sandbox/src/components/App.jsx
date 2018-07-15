import React, { Component } from "react"

import Column from "./Column.jsx";
import Row from './Row.jsx';
import Container from './Container.jsx';
import Button from '../components/Button';
import TextInput from '../components/TextInput';
import Span from '../components/Span';
import P from '../components/P';

class App extends Component {
  render() {

 

    return (
      <Container extraClasses="m-5 view">
        <Row>
          <Column extraClasses={"shadow m-1 background-color-opacity-3"}>
          <Button
              extraClasses={"button-base m-4 background-color-transparent color-white border-1-white opacity-60"}
              label="two"
          ></Button>
          <br></br>
          <Span text="Text" extraClasses="label-text">
          </Span>
          <Column>
            <TextInput
                    extraClasses="input-base"
                    labeltext="label"
                    placeholder="placeholder"
              ></TextInput>
          </Column> 
          </Column>
        </Row>
      </Container>
    )
  }
}

export default App
