import axios from "axios";

const API_URL = "http://localhost:8080/api/users";

const register = (username, email, phone, password, birth, pfp, gender, topics, role) => {
  return axios.post(API_URL, {
    username,
    email,
    phone,
    password,
    birth,
    pfp,
    gender,
    topics,
    role
  });
};

const login = (username, password) => {
  return axios
    .get(API_URL + "/name/" + username)
    .then((response) => {
      if (response.data.username) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
      return response.data
    });
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
  register,
  login,
  getCurrentUser
}

export default AuthService;