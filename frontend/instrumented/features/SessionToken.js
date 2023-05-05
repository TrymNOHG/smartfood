function cov_1cacb7fhts() {
  var path = "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\features\\SessionToken.js";
  var hash = "ca123262fe8a0e55f90809707e6c4384c1ff6aed";
  var global = new Function("return this")();
  var gcv = "__coverage__";
  var coverageData = {
    path: "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\features\\SessionToken.js",
    statementMap: {
      "0": {
        start: {
          line: 5,
          column: 25
        },
        end: {
          line: 5,
          column: 59
        }
      },
      "1": {
        start: {
          line: 6,
          column: 4
        },
        end: {
          line: 10,
          column: 5
        }
      },
      "2": {
        start: {
          line: 7,
          column: 8
        },
        end: {
          line: 7,
          column: 47
        }
      },
      "3": {
        start: {
          line: 8,
          column: 8
        },
        end: {
          line: 8,
          column: 35
        }
      },
      "4": {
        start: {
          line: 9,
          column: 8
        },
        end: {
          line: 9,
          column: 72
        }
      },
      "5": {
        start: {
          line: 12,
          column: 4
        },
        end: {
          line: 12,
          column: 23
        }
      }
    },
    fnMap: {
      "0": {
        name: "sessionToken",
        decl: {
          start: {
            line: 4,
            column: 30
          },
          end: {
            line: 4,
            column: 42
          }
        },
        loc: {
          start: {
            line: 4,
            column: 46
          },
          end: {
            line: 13,
            column: 1
          }
        },
        line: 4
      }
    },
    branchMap: {
      "0": {
        loc: {
          start: {
            line: 6,
            column: 4
          },
          end: {
            line: 10,
            column: 5
          }
        },
        type: "if",
        locations: [{
          start: {
            line: 6,
            column: 4
          },
          end: {
            line: 10,
            column: 5
          }
        }, {
          start: {
            line: 6,
            column: 4
          },
          end: {
            line: 10,
            column: 5
          }
        }],
        line: 6
      }
    },
    s: {
      "0": 0,
      "1": 0,
      "2": 0,
      "3": 0,
      "4": 0,
      "5": 0
    },
    f: {
      "0": 0
    },
    b: {
      "0": [0, 0]
    },
    _coverageSchema: "1a1c01bbd47fc00a2c39e90264f33305004495a9",
    hash: "ca123262fe8a0e55f90809707e6c4384c1ff6aed"
  };
  var coverage = global[gcv] || (global[gcv] = {});
  if (!coverage[path] || coverage[path].hash !== hash) {
    coverage[path] = coverageData;
  }
  var actualCoverage = coverage[path];
  {
    // @ts-ignore
    cov_1cacb7fhts = function () {
      return actualCoverage;
    };
  }
  return actualCoverage;
}
cov_1cacb7fhts();
import { useLoggedInStore } from "@/store/store";
import router from "@/router/router";
export default async function sessionToken() {
  cov_1cacb7fhts().f[0]++;
  const sessionToken = (cov_1cacb7fhts().s[0]++, useLoggedInStore().getSessionToken);
  cov_1cacb7fhts().s[1]++;
  if (sessionToken === null) {
    cov_1cacb7fhts().b[0][0]++;
    cov_1cacb7fhts().s[2]++;
    alert("Log in to access your profile!"); //TODO: make better
    cov_1cacb7fhts().s[3]++;
    await router.push("/login");
    cov_1cacb7fhts().s[4]++;
    throw new Error("Session token cannot be null. Login in again.");
  } else {
    cov_1cacb7fhts().b[0][1]++;
  }
  cov_1cacb7fhts().s[5]++;
  return sessionToken;
}
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJuYW1lcyI6WyJjb3ZfMWNhY2I3Zmh0cyIsImFjdHVhbENvdmVyYWdlIiwidXNlTG9nZ2VkSW5TdG9yZSIsInJvdXRlciIsInNlc3Npb25Ub2tlbiIsImYiLCJzIiwiZ2V0U2Vzc2lvblRva2VuIiwiYiIsImFsZXJ0IiwicHVzaCIsIkVycm9yIl0sInNvdXJjZXMiOlsiU2Vzc2lvblRva2VuLmpzIl0sInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7dXNlTG9nZ2VkSW5TdG9yZX0gZnJvbSBcIkAvc3RvcmUvc3RvcmVcIjtcclxuaW1wb3J0IHJvdXRlciBmcm9tIFwiQC9yb3V0ZXIvcm91dGVyXCI7XHJcblxyXG5leHBvcnQgZGVmYXVsdCBhc3luYyBmdW5jdGlvbiBzZXNzaW9uVG9rZW4gKCkge1xyXG4gICAgY29uc3Qgc2Vzc2lvblRva2VuID0gdXNlTG9nZ2VkSW5TdG9yZSgpLmdldFNlc3Npb25Ub2tlblxyXG4gICAgaWYgKHNlc3Npb25Ub2tlbiA9PT0gbnVsbCkge1xyXG4gICAgICAgIGFsZXJ0KFwiTG9nIGluIHRvIGFjY2VzcyB5b3VyIHByb2ZpbGUhXCIpIC8vVE9ETzogbWFrZSBiZXR0ZXJcclxuICAgICAgICBhd2FpdCByb3V0ZXIucHVzaChcIi9sb2dpblwiKVxyXG4gICAgICAgIHRocm93IG5ldyBFcnJvcihcIlNlc3Npb24gdG9rZW4gY2Fubm90IGJlIG51bGwuIExvZ2luIGluIGFnYWluLlwiKVxyXG4gICAgfVxyXG5cclxuICAgIHJldHVybiBzZXNzaW9uVG9rZW5cclxufSJdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7O0lBZVk7SUFBQUEsY0FBQSxZQUFBQSxDQUFBO01BQUEsT0FBQUMsY0FBQTtJQUFBO0VBQUE7RUFBQSxPQUFBQSxjQUFBO0FBQUE7QUFBQUQsY0FBQTtBQWZaLFNBQVFFLGdCQUFnQixRQUFPLGVBQWU7QUFDOUMsT0FBT0MsTUFBTSxNQUFNLGlCQUFpQjtBQUVwQyxlQUFlLGVBQWVDLFlBQVlBLENBQUEsRUFBSTtFQUFBSixjQUFBLEdBQUFLLENBQUE7RUFDMUMsTUFBTUQsWUFBWSxJQUFBSixjQUFBLEdBQUFNLENBQUEsT0FBR0osZ0JBQWdCLENBQUMsQ0FBQyxDQUFDSyxlQUFlO0VBQUFQLGNBQUEsR0FBQU0sQ0FBQTtFQUN2RCxJQUFJRixZQUFZLEtBQUssSUFBSSxFQUFFO0lBQUFKLGNBQUEsR0FBQVEsQ0FBQTtJQUFBUixjQUFBLEdBQUFNLENBQUE7SUFDdkJHLEtBQUssQ0FBQyxnQ0FBZ0MsQ0FBQyxFQUFDO0lBQUFULGNBQUEsR0FBQU0sQ0FBQTtJQUN4QyxNQUFNSCxNQUFNLENBQUNPLElBQUksQ0FBQyxRQUFRLENBQUM7SUFBQVYsY0FBQSxHQUFBTSxDQUFBO0lBQzNCLE1BQU0sSUFBSUssS0FBSyxDQUFDLCtDQUErQyxDQUFDO0VBQ3BFLENBQUM7SUFBQVgsY0FBQSxHQUFBUSxDQUFBO0VBQUE7RUFBQVIsY0FBQSxHQUFBTSxDQUFBO0VBRUQsT0FBT0YsWUFBWTtBQUN2QiJ9