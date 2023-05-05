function cov_s7426xgml() {
  var path = "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\locales\\i18n.js";
  var hash = "21de96fff3b4365cd27a3ffbca782bc3834c1feb";
  var global = new Function("return this")();
  var gcv = "__coverage__";
  var coverageData = {
    path: "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\locales\\i18n.js",
    statementMap: {
      "0": {
        start: {
          line: 6,
          column: 13
        },
        end: {
          line: 13,
          column: 2
        }
      }
    },
    fnMap: {},
    branchMap: {},
    s: {
      "0": 0
    },
    f: {},
    b: {},
    _coverageSchema: "1a1c01bbd47fc00a2c39e90264f33305004495a9",
    hash: "21de96fff3b4365cd27a3ffbca782bc3834c1feb"
  };
  var coverage = global[gcv] || (global[gcv] = {});
  if (!coverage[path] || coverage[path].hash !== hash) {
    coverage[path] = coverageData;
  }
  var actualCoverage = coverage[path];
  {
    // @ts-ignore
    cov_s7426xgml = function () {
      return actualCoverage;
    };
  }
  return actualCoverage;
}
cov_s7426xgml();
import { createI18n } from 'vue-i18n';
import en from "./en.json";
import no from "./no.json";
const i18n = (cov_s7426xgml().s[0]++, createI18n({
  legacy: false,
  locale: "NO",
  messages: {
    EN: en,
    NO: no
  }
}));
export default i18n;
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJuYW1lcyI6WyJjb3Zfczc0MjZ4Z21sIiwiYWN0dWFsQ292ZXJhZ2UiLCJjcmVhdGVJMThuIiwiZW4iLCJubyIsImkxOG4iLCJzIiwibGVnYWN5IiwibG9jYWxlIiwibWVzc2FnZXMiLCJFTiIsIk5PIl0sInNvdXJjZXMiOlsiaTE4bi5qcyJdLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBjcmVhdGVJMThuIH0gZnJvbSAndnVlLWkxOG4nXHJcbmltcG9ydCBlbiBmcm9tIFwiLi9lbi5qc29uXCJcclxuaW1wb3J0IG5vIGZyb20gXCIuL25vLmpzb25cIlxyXG5cclxuXHJcbmNvbnN0IGkxOG4gPSBjcmVhdGVJMThuKHtcclxuICAgIGxlZ2FjeTogZmFsc2UsXHJcbiAgICBsb2NhbGU6IFwiTk9cIixcclxuICAgIG1lc3NhZ2VzOiB7XHJcbiAgICAgICAgRU46IGVuLFxyXG4gICAgICAgIE5POiBubyxcclxuICAgIH0sXHJcbn0pO1xyXG5cclxuZXhwb3J0IGRlZmF1bHQgaTE4bjtcclxuIl0sIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OztJQWVZO0lBQUFBLGFBQUEsWUFBQUEsQ0FBQTtNQUFBLE9BQUFDLGNBQUE7SUFBQTtFQUFBO0VBQUEsT0FBQUEsY0FBQTtBQUFBO0FBQUFELGFBQUE7QUFmWixTQUFTRSxVQUFVLFFBQVEsVUFBVTtBQUNyQyxPQUFPQyxFQUFFLE1BQU0sV0FBVztBQUMxQixPQUFPQyxFQUFFLE1BQU0sV0FBVztBQUcxQixNQUFNQyxJQUFJLElBQUFMLGFBQUEsR0FBQU0sQ0FBQSxPQUFHSixVQUFVLENBQUM7RUFDcEJLLE1BQU0sRUFBRSxLQUFLO0VBQ2JDLE1BQU0sRUFBRSxJQUFJO0VBQ1pDLFFBQVEsRUFBRTtJQUNOQyxFQUFFLEVBQUVQLEVBQUU7SUFDTlEsRUFBRSxFQUFFUDtFQUNSO0FBQ0osQ0FBQyxDQUFDO0FBRUYsZUFBZUMsSUFBSSJ9