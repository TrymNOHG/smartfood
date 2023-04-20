import axios from "axios";

const BASE_API_URL = "https://kassal.app/api/v1";

export const getItems = async (searchQuery) => {
    return await axios.get(`${BASE_API_URL}/products?search=${searchQuery}`, {
      headers: {
        Authorization: "Bearer lWLt2onXRYSUgtMTkeJQq5i4dP6XhHPkl7ywLOSX",
      },
    });
  }
  

  //return (await response).data.data;

