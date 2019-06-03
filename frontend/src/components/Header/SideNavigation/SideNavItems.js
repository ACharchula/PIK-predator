import './SideNavigation.scss';
import React, { Component } from 'react'
import Form from "react-bootstrap/Form";
import axios from "axios";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {addFilter, clearFilters, removeFilter} from "../../../redux/actions";

class SideNavItems extends Component {

    constructor(props) {
        super(props);
        this.state = {
            items: [
                {
                    type: "option",
                    text: 'CPU',
                    columnName: 'processor',
                    options: []
                },
                {
                    type: "option",
                    text: "Hard Drive Type",
                    columnName: 'hardDriveType',
                    options: []
                },
                {
                    type: "option",
                    text: 'Display',
                    columnName: 'displayResolution',
                    options: []
                }
            ]
        };
    }

    componentWillMount() {
        this.state.items.map(item => {
            axios.get(`https://pik-predator.herokuapp.com/catalog/metadata/${item.columnName}`)
                .then(response => {
                    item.options = response.data;
                })
            return item;
        });
    };

    handleChange(event) {
        //this.props.clearFilters();
        let pos=0;
        let values =event.target.name.split('_');
        let type=values[0];
        let value=values[1];
        let flag=0;
        let whichone="";
        if(this.props.filter.filters[0]!==null) {
            while(pos!==-1) {
                pos = this.props.filter.filters.map(function (e) {
                    return e.value;
                }).indexOf(value);
                if(pos!==-1) {
                    this.props.removeFilter(pos,type);
                    flag=1;
                    pos=-1;
                }
            }
            while(pos!==-1) {
                pos = this.props.filter.filters.map(function (e) {
                    return e.property;
                }).indexOf(type);
                if(pos!==-1) {
                    this.props.removeFilter(pos);
                    flag=1;
                    pos=-1;
                }
            }


        }
        if(flag!==1) this.props.addFilter({property: type, value: value})

    }

     getCheckboxes = (item) => {
        return item.options.map((option) => {
                return (
                    <Form.Check
                        name={item.columnName+"_"+option}
                        onChange={this.handleChange.bind(this)}
                        id={item.text+option}
                        key={item.text+option}
                        label={option}
                    />
                )
            }
        )
    }


     showItems = () => {
        return this.state.items.map((item, i) => {
            return (
                <div className={item.type} key={i}>
                    {item.text}
                    <Form>
                        <Form.Group>
                            {this.getCheckboxes(item)}
                        </Form.Group>
                    </Form>
                </div>
            )
        })
    }

    render = () => {
        return (
            <div>
                {this.showItems()}
            </div>
        )
    }
}


const mapStateToProps = (state) => {
    return {
        filter: state.filter,
    }
}

const mapDispatchToProps = (dispatch) => {
    return bindActionCreators({ addFilter,removeFilter,clearFilters }, dispatch);
}


//export default SideNavItems;
export default connect(mapStateToProps,mapDispatchToProps)(SideNavItems);
