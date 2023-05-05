function cov_2el4zwza8o() {
  var path = "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\services\\NotificationService.js";
  var hash = "c01d8aa1259dc6efd296d54a03b1776f0444c742";
  var global = new Function("return this")();
  var gcv = "__coverage__";
  var coverageData = {
    path: "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\services\\NotificationService.js",
    statementMap: {
      "0": {
        start: {
          line: 4,
          column: 25
        },
        end: {
          line: 4,
          column: 65
        }
      },
      "1": {
        start: {
          line: 6,
          column: 32
        },
        end: {
          line: 12,
          column: 1
        }
      },
      "2": {
        start: {
          line: 7,
          column: 4
        },
        end: {
          line: 11,
          column: 7
        }
      },
      "3": {
        start: {
          line: 14,
          column: 32
        },
        end: {
          line: 20,
          column: 1
        }
      },
      "4": {
        start: {
          line: 15,
          column: 4
        },
        end: {
          line: 19,
          column: 7
        }
      },
      "5": {
        start: {
          line: 23,
          column: 28
        },
        end: {
          line: 29,
          column: 1
        }
      },
      "6": {
        start: {
          line: 24,
          column: 4
        },
        end: {
          line: 28,
          column: 7
        }
      }
    },
    fnMap: {
      "0": {
        name: "(anonymous_0)",
        decl: {
          start: {
            line: 6,
            column: 32
          },
          end: {
            line: 6,
            column: 33
          }
        },
        loc: {
          start: {
            line: 6,
            column: 44
          },
          end: {
            line: 12,
            column: 1
          }
        },
        line: 6
      },
      "1": {
        name: "(anonymous_1)",
        decl: {
          start: {
            line: 14,
            column: 32
          },
          end: {
            line: 14,
            column: 33
          }
        },
        loc: {
          start: {
            line: 14,
            column: 44
          },
          end: {
            line: 20,
            column: 1
          }
        },
        line: 14
      },
      "2": {
        name: "(anonymous_2)",
        decl: {
          start: {
            line: 23,
            column: 28
          },
          end: {
            line: 23,
            column: 29
          }
        },
        loc: {
          start: {
            line: 23,
            column: 40
          },
          end: {
            line: 29,
            column: 1
          }
        },
        line: 23
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
      "6": 0
    },
    f: {
      "0": 0,
      "1": 0,
      "2": 0
    },
    b: {},
    _coverageSchema: "1a1c01bbd47fc00a2c39e90264f33305004495a9",
    hash: "c01d8aa1259dc6efd296d54a03b1776f0444c742"
  };
  var coverage = global[gcv] || (global[gcv] = {});
  if (!coverage[path] || coverage[path].hash !== hash) {
    coverage[path] = coverageData;
  }
  var actualCoverage = coverage[path];
  {
    // @ts-ignore
    cov_2el4zwza8o = function () {
      return actualCoverage;
    };
  }
  return actualCoverage;
}
cov_2el4zwza8o();
import axios from "axios";
import SessionToken from "@/features/SessionToken";
const BASE_LISTING_URL = (cov_2el4zwza8o().s[0]++, "http://localhost:8089/api/notification");
cov_2el4zwza8o().s[1]++;
export const getNotifications = async () => {
  cov_2el4zwza8o().f[0]++;
  cov_2el4zwza8o().s[2]++;
  return await axios.get(`${BASE_LISTING_URL}/get`, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`
    }
  });
};
cov_2el4zwza8o().s[3]++;
export const setNotifications = async () => {
  cov_2el4zwza8o().f[1]++;
  cov_2el4zwza8o().s[4]++;
  await axios.get(`${BASE_LISTING_URL}/update`, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`
    }
  });
};
cov_2el4zwza8o().s[5]++;
export const removeBorder = async () => {
  cov_2el4zwza8o().f[2]++;
  cov_2el4zwza8o().s[6]++;
  await axios.put(`${BASE_LISTING_URL}/read/all`, {}, {
    headers: {
      Authorization: `Bearer ${await SessionToken()}`
    }
  });
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJuYW1lcyI6WyJjb3ZfMmVsNHp3emE4byIsImFjdHVhbENvdmVyYWdlIiwiYXhpb3MiLCJTZXNzaW9uVG9rZW4iLCJCQVNFX0xJU1RJTkdfVVJMIiwicyIsImdldE5vdGlmaWNhdGlvbnMiLCJmIiwiZ2V0IiwiaGVhZGVycyIsIkF1dGhvcml6YXRpb24iLCJzZXROb3RpZmljYXRpb25zIiwicmVtb3ZlQm9yZGVyIiwicHV0Il0sInNvdXJjZXMiOlsiTm90aWZpY2F0aW9uU2VydmljZS5qcyJdLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgYXhpb3MgZnJvbSBcImF4aW9zXCI7XHJcbmltcG9ydCBTZXNzaW9uVG9rZW4gZnJvbSBcIkAvZmVhdHVyZXMvU2Vzc2lvblRva2VuXCI7XHJcblxyXG5jb25zdCBCQVNFX0xJU1RJTkdfVVJMID0gXCJodHRwOi8vbG9jYWxob3N0OjgwODkvYXBpL25vdGlmaWNhdGlvblwiXHJcblxyXG5leHBvcnQgY29uc3QgZ2V0Tm90aWZpY2F0aW9ucyA9IGFzeW5jICgpID0+IHtcclxuICAgIHJldHVybiBhd2FpdCBheGlvcy5nZXQoYCR7QkFTRV9MSVNUSU5HX1VSTH0vZ2V0YCwge1xyXG4gICAgICAgIGhlYWRlcnM6IHtcclxuICAgICAgICAgICAgQXV0aG9yaXphdGlvbjogYEJlYXJlciAke2F3YWl0IFNlc3Npb25Ub2tlbigpfWAsXHJcbiAgICAgICAgfSxcclxuICAgIH0pO1xyXG59XHJcblxyXG5leHBvcnQgY29uc3Qgc2V0Tm90aWZpY2F0aW9ucyA9IGFzeW5jICgpID0+IHtcclxuICAgIGF3YWl0IGF4aW9zLmdldChgJHtCQVNFX0xJU1RJTkdfVVJMfS91cGRhdGVgLCB7XHJcbiAgICAgICAgaGVhZGVyczoge1xyXG4gICAgICAgICAgICBBdXRob3JpemF0aW9uOiBgQmVhcmVyICR7YXdhaXQgU2Vzc2lvblRva2VuKCl9YCxcclxuICAgICAgICB9LFxyXG4gICAgfSk7XHJcbn07XHJcblxyXG5cclxuZXhwb3J0IGNvbnN0IHJlbW92ZUJvcmRlciA9IGFzeW5jICgpID0+IHtcclxuICAgIGF3YWl0IGF4aW9zLnB1dChgJHtCQVNFX0xJU1RJTkdfVVJMfS9yZWFkL2FsbGAsIHt9LCB7XHJcbiAgICAgICAgaGVhZGVyczoge1xyXG4gICAgICAgICAgICBBdXRob3JpemF0aW9uOiBgQmVhcmVyICR7YXdhaXQgU2Vzc2lvblRva2VuKCl9YCxcclxuICAgICAgICB9LFxyXG4gICAgfSk7XHJcbn1cclxuIl0sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7O0lBZVk7SUFBQUEsY0FBQSxZQUFBQSxDQUFBO01BQUEsT0FBQUMsY0FBQTtJQUFBO0VBQUE7RUFBQSxPQUFBQSxjQUFBO0FBQUE7QUFBQUQsY0FBQTtBQWZaLE9BQU9FLEtBQUssTUFBTSxPQUFPO0FBQ3pCLE9BQU9DLFlBQVksTUFBTSx5QkFBeUI7QUFFbEQsTUFBTUMsZ0JBQWdCLElBQUFKLGNBQUEsR0FBQUssQ0FBQSxPQUFHLHdDQUF3QztBQUFBTCxjQUFBLEdBQUFLLENBQUE7QUFFakUsT0FBTyxNQUFNQyxnQkFBZ0IsR0FBRyxNQUFBQSxDQUFBLEtBQVk7RUFBQU4sY0FBQSxHQUFBTyxDQUFBO0VBQUFQLGNBQUEsR0FBQUssQ0FBQTtFQUN4QyxPQUFPLE1BQU1ILEtBQUssQ0FBQ00sR0FBRyxDQUFFLEdBQUVKLGdCQUFpQixNQUFLLEVBQUU7SUFDOUNLLE9BQU8sRUFBRTtNQUNMQyxhQUFhLEVBQUcsVUFBUyxNQUFNUCxZQUFZLENBQUMsQ0FBRTtJQUNsRDtFQUNKLENBQUMsQ0FBQztBQUNOLENBQUM7QUFBQUgsY0FBQSxHQUFBSyxDQUFBO0FBRUQsT0FBTyxNQUFNTSxnQkFBZ0IsR0FBRyxNQUFBQSxDQUFBLEtBQVk7RUFBQVgsY0FBQSxHQUFBTyxDQUFBO0VBQUFQLGNBQUEsR0FBQUssQ0FBQTtFQUN4QyxNQUFNSCxLQUFLLENBQUNNLEdBQUcsQ0FBRSxHQUFFSixnQkFBaUIsU0FBUSxFQUFFO0lBQzFDSyxPQUFPLEVBQUU7TUFDTEMsYUFBYSxFQUFHLFVBQVMsTUFBTVAsWUFBWSxDQUFDLENBQUU7SUFDbEQ7RUFDSixDQUFDLENBQUM7QUFDTixDQUFDO0FBQUNILGNBQUEsR0FBQUssQ0FBQTtBQUdGLE9BQU8sTUFBTU8sWUFBWSxHQUFHLE1BQUFBLENBQUEsS0FBWTtFQUFBWixjQUFBLEdBQUFPLENBQUE7RUFBQVAsY0FBQSxHQUFBSyxDQUFBO0VBQ3BDLE1BQU1ILEtBQUssQ0FBQ1csR0FBRyxDQUFFLEdBQUVULGdCQUFpQixXQUFVLEVBQUUsQ0FBQyxDQUFDLEVBQUU7SUFDaERLLE9BQU8sRUFBRTtNQUNMQyxhQUFhLEVBQUcsVUFBUyxNQUFNUCxZQUFZLENBQUMsQ0FBRTtJQUNsRDtFQUNKLENBQUMsQ0FBQztBQUNOLENBQUMifQ==