function cov_1jey4swjgs() {
  var path = "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\services\\ApiService.js";
  var hash = "9b987fa25edda2c6f19963656ed8e2969d256ad1";
  var global = new Function("return this")();
  var gcv = "__coverage__";
  var coverageData = {
    path: "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\services\\ApiService.js",
    statementMap: {
      "0": {
        start: {
          line: 3,
          column: 21
        },
        end: {
          line: 3,
          column: 48
        }
      },
      "1": {
        start: {
          line: 5,
          column: 24
        },
        end: {
          line: 15,
          column: 1
        }
      },
      "2": {
        start: {
          line: 6,
          column: 19
        },
        end: {
          line: 13,
          column: 3
        }
      },
      "3": {
        start: {
          line: 14,
          column: 2
        },
        end: {
          line: 14,
          column: 36
        }
      },
      "4": {
        start: {
          line: 17,
          column: 30
        },
        end: {
          line: 27,
          column: 1
        }
      },
      "5": {
        start: {
          line: 18,
          column: 21
        },
        end: {
          line: 25,
          column: 5
        }
      },
      "6": {
        start: {
          line: 26,
          column: 4
        },
        end: {
          line: 26,
          column: 38
        }
      },
      "7": {
        start: {
          line: 30,
          column: 32
        },
        end: {
          line: 40,
          column: 1
        }
      },
      "8": {
        start: {
          line: 31,
          column: 19
        },
        end: {
          line: 38,
          column: 3
        }
      },
      "9": {
        start: {
          line: 39,
          column: 2
        },
        end: {
          line: 39,
          column: 36
        }
      }
    },
    fnMap: {
      "0": {
        name: "(anonymous_0)",
        decl: {
          start: {
            line: 5,
            column: 24
          },
          end: {
            line: 5,
            column: 25
          }
        },
        loc: {
          start: {
            line: 5,
            column: 47
          },
          end: {
            line: 15,
            column: 1
          }
        },
        line: 5
      },
      "1": {
        name: "(anonymous_1)",
        decl: {
          start: {
            line: 17,
            column: 30
          },
          end: {
            line: 17,
            column: 31
          }
        },
        loc: {
          start: {
            line: 17,
            column: 61
          },
          end: {
            line: 27,
            column: 1
          }
        },
        line: 17
      },
      "2": {
        name: "(anonymous_2)",
        decl: {
          start: {
            line: 30,
            column: 32
          },
          end: {
            line: 30,
            column: 33
          }
        },
        loc: {
          start: {
            line: 30,
            column: 51
          },
          end: {
            line: 40,
            column: 1
          }
        },
        line: 30
      }
    },
    branchMap: {},
    s: {
      "0": 0,
      "1": 0,
      "2": 0,
      "3": 0,
      "4": 0,
      "5": 0,
      "6": 0,
      "7": 0,
      "8": 0,
      "9": 0
    },
    f: {
      "0": 0,
      "1": 0,
      "2": 0
    },
    b: {},
    _coverageSchema: "1a1c01bbd47fc00a2c39e90264f33305004495a9",
    hash: "9b987fa25edda2c6f19963656ed8e2969d256ad1"
  };
  var coverage = global[gcv] || (global[gcv] = {});
  if (!coverage[path] || coverage[path].hash !== hash) {
    coverage[path] = coverageData;
  }
  var actualCoverage = coverage[path];
  {
    // @ts-ignore
    cov_1jey4swjgs = function () {
      return actualCoverage;
    };
  }
  return actualCoverage;
}
cov_1jey4swjgs();
import axios from "axios";
const BASE_API_URL = (cov_1jey4swjgs().s[0]++, "https://kassal.app/api/v1");
cov_1jey4swjgs().s[1]++;
export const getItems = async searchQuery => {
  cov_1jey4swjgs().f[0]++;
  const response = (cov_1jey4swjgs().s[2]++, axios.get(`https://kassal.app/api/v1/products?search=${searchQuery}`, {
    headers: {
      Authorization: `Bearer lWLt2onXRYSUgtMTkeJQq5i4dP6XhHPkl7ywLOSX`
    }
  }));
  cov_1jey4swjgs().s[3]++;
  return (await response).data.data;
};
cov_1jey4swjgs().s[4]++;
export const getItemsByPage = async (searchQuery, pageNr) => {
  cov_1jey4swjgs().f[1]++;
  const response = (cov_1jey4swjgs().s[5]++, axios.get(`https://kassal.app/api/v1/products?search=${searchQuery}&page=${pageNr}`, {
    headers: {
      Authorization: `Bearer lWLt2onXRYSUgtMTkeJQq5i4dP6XhHPkl7ywLOSX`
    }
  }));
  cov_1jey4swjgs().s[6]++;
  return (await response).data.data;
};
cov_1jey4swjgs().s[7]++;
export const getItemByBarcode = async barcode => {
  cov_1jey4swjgs().f[2]++;
  const response = (cov_1jey4swjgs().s[8]++, axios.get(`https://kassal.app/api/v1/products/ean/${barcode}`, {
    headers: {
      Authorization: `Bearer lWLt2onXRYSUgtMTkeJQq5i4dP6XhHPkl7ywLOSX`
    }
  }));
  cov_1jey4swjgs().s[9]++;
  return (await response).data.data;
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJuYW1lcyI6WyJjb3ZfMWpleTRzd2pncyIsImFjdHVhbENvdmVyYWdlIiwiYXhpb3MiLCJCQVNFX0FQSV9VUkwiLCJzIiwiZ2V0SXRlbXMiLCJzZWFyY2hRdWVyeSIsImYiLCJyZXNwb25zZSIsImdldCIsImhlYWRlcnMiLCJBdXRob3JpemF0aW9uIiwiZGF0YSIsImdldEl0ZW1zQnlQYWdlIiwicGFnZU5yIiwiZ2V0SXRlbUJ5QmFyY29kZSIsImJhcmNvZGUiXSwic291cmNlcyI6WyJBcGlTZXJ2aWNlLmpzIl0sInNvdXJjZXNDb250ZW50IjpbImltcG9ydCBheGlvcyBmcm9tIFwiYXhpb3NcIjtcclxuXHJcbmNvbnN0IEJBU0VfQVBJX1VSTCA9IFwiaHR0cHM6Ly9rYXNzYWwuYXBwL2FwaS92MVwiO1xyXG5cclxuZXhwb3J0IGNvbnN0IGdldEl0ZW1zID0gYXN5bmMgKHNlYXJjaFF1ZXJ5KSA9PiB7XHJcbiAgY29uc3QgcmVzcG9uc2UgPSBheGlvcy5nZXQoXHJcbiAgICBgaHR0cHM6Ly9rYXNzYWwuYXBwL2FwaS92MS9wcm9kdWN0cz9zZWFyY2g9JHtzZWFyY2hRdWVyeX1gLFxyXG4gICAge1xyXG4gICAgICBoZWFkZXJzOiB7XHJcbiAgICAgICAgQXV0aG9yaXphdGlvbjogYEJlYXJlciBsV0x0Mm9uWFJZU1VndE1Ua2VKUXE1aTRkUDZYaEhQa2w3eXdMT1NYYCxcclxuICAgICAgfSxcclxuICAgIH1cclxuICApO1xyXG4gIHJldHVybiAoYXdhaXQgcmVzcG9uc2UpLmRhdGEuZGF0YTtcclxufTtcclxuXHJcbmV4cG9ydCBjb25zdCBnZXRJdGVtc0J5UGFnZSA9IGFzeW5jIChzZWFyY2hRdWVyeSwgcGFnZU5yKSA9PiB7XHJcbiAgICBjb25zdCByZXNwb25zZSA9IGF4aW9zLmdldChcclxuICAgICAgICBgaHR0cHM6Ly9rYXNzYWwuYXBwL2FwaS92MS9wcm9kdWN0cz9zZWFyY2g9JHtzZWFyY2hRdWVyeX0mcGFnZT0ke3BhZ2VOcn1gLFxyXG4gICAgICAgIHtcclxuICAgICAgICAgICAgaGVhZGVyczoge1xyXG4gICAgICAgICAgICAgICAgQXV0aG9yaXphdGlvbjogYEJlYXJlciBsV0x0Mm9uWFJZU1VndE1Ua2VKUXE1aTRkUDZYaEhQa2w3eXdMT1NYYCxcclxuICAgICAgICAgICAgfSxcclxuICAgICAgICB9XHJcbiAgICApO1xyXG4gICAgcmV0dXJuIChhd2FpdCByZXNwb25zZSkuZGF0YS5kYXRhO1xyXG59O1xyXG5cclxuXHJcbmV4cG9ydCBjb25zdCBnZXRJdGVtQnlCYXJjb2RlID0gYXN5bmMgKGJhcmNvZGUpID0+IHtcclxuICBjb25zdCByZXNwb25zZSA9IGF4aW9zLmdldChcclxuICAgIGBodHRwczovL2thc3NhbC5hcHAvYXBpL3YxL3Byb2R1Y3RzL2Vhbi8ke2JhcmNvZGV9YCxcclxuICAgIHtcclxuICAgICAgaGVhZGVyczoge1xyXG4gICAgICAgIEF1dGhvcml6YXRpb246IGBCZWFyZXIgbFdMdDJvblhSWVNVZ3RNVGtlSlFxNWk0ZFA2WGhIUGtsN3l3TE9TWGAsXHJcbiAgICAgIH0sXHJcbiAgICB9XHJcbiAgKTtcclxuICByZXR1cm4gKGF3YWl0IHJlc3BvbnNlKS5kYXRhLmRhdGE7XHJcbn07XHJcbiJdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OztJQWVZO0lBQUFBLGNBQUEsWUFBQUEsQ0FBQTtNQUFBLE9BQUFDLGNBQUE7SUFBQTtFQUFBO0VBQUEsT0FBQUEsY0FBQTtBQUFBO0FBQUFELGNBQUE7QUFmWixPQUFPRSxLQUFLLE1BQU0sT0FBTztBQUV6QixNQUFNQyxZQUFZLElBQUFILGNBQUEsR0FBQUksQ0FBQSxPQUFHLDJCQUEyQjtBQUFDSixjQUFBLEdBQUFJLENBQUE7QUFFakQsT0FBTyxNQUFNQyxRQUFRLEdBQUcsTUFBT0MsV0FBVyxJQUFLO0VBQUFOLGNBQUEsR0FBQU8sQ0FBQTtFQUM3QyxNQUFNQyxRQUFRLElBQUFSLGNBQUEsR0FBQUksQ0FBQSxPQUFHRixLQUFLLENBQUNPLEdBQUcsQ0FDdkIsNkNBQTRDSCxXQUFZLEVBQUMsRUFDMUQ7SUFDRUksT0FBTyxFQUFFO01BQ1BDLGFBQWEsRUFBRztJQUNsQjtFQUNGLENBQ0YsQ0FBQztFQUFDWCxjQUFBLEdBQUFJLENBQUE7RUFDRixPQUFPLENBQUMsTUFBTUksUUFBUSxFQUFFSSxJQUFJLENBQUNBLElBQUk7QUFDbkMsQ0FBQztBQUFDWixjQUFBLEdBQUFJLENBQUE7QUFFRixPQUFPLE1BQU1TLGNBQWMsR0FBRyxNQUFBQSxDQUFPUCxXQUFXLEVBQUVRLE1BQU0sS0FBSztFQUFBZCxjQUFBLEdBQUFPLENBQUE7RUFDekQsTUFBTUMsUUFBUSxJQUFBUixjQUFBLEdBQUFJLENBQUEsT0FBR0YsS0FBSyxDQUFDTyxHQUFHLENBQ3JCLDZDQUE0Q0gsV0FBWSxTQUFRUSxNQUFPLEVBQUMsRUFDekU7SUFDSUosT0FBTyxFQUFFO01BQ0xDLGFBQWEsRUFBRztJQUNwQjtFQUNKLENBQ0osQ0FBQztFQUFDWCxjQUFBLEdBQUFJLENBQUE7RUFDRixPQUFPLENBQUMsTUFBTUksUUFBUSxFQUFFSSxJQUFJLENBQUNBLElBQUk7QUFDckMsQ0FBQztBQUFDWixjQUFBLEdBQUFJLENBQUE7QUFHRixPQUFPLE1BQU1XLGdCQUFnQixHQUFHLE1BQU9DLE9BQU8sSUFBSztFQUFBaEIsY0FBQSxHQUFBTyxDQUFBO0VBQ2pELE1BQU1DLFFBQVEsSUFBQVIsY0FBQSxHQUFBSSxDQUFBLE9BQUdGLEtBQUssQ0FBQ08sR0FBRyxDQUN2QiwwQ0FBeUNPLE9BQVEsRUFBQyxFQUNuRDtJQUNFTixPQUFPLEVBQUU7TUFDUEMsYUFBYSxFQUFHO0lBQ2xCO0VBQ0YsQ0FDRixDQUFDO0VBQUNYLGNBQUEsR0FBQUksQ0FBQTtFQUNGLE9BQU8sQ0FBQyxNQUFNSSxRQUFRLEVBQUVJLElBQUksQ0FBQ0EsSUFBSTtBQUNuQyxDQUFDIn0=