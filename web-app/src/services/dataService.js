import axios from "axios";

const API_URL = "http://localhost:8080/api/";

const getEntries = () => {
  return axios.get(API_URL + "entries");
};

const getComments = () => {
  return axios.get(API_URL + "comments");
};

const DataService = {
    getEntries,
    getComments,
}

export default DataService;