const auth = window.APP_CONTEXT?.auth || "";
console.log("권한:", auth);

/** 메뉴 데이터 */
const MENUS = {
  accounting: {
    title: "회계",
    content: "회계 대시보드",
    groups: [
      { title: "계정 과목", items: [["계정 과목 관리", "/accountList"]] },
      {
        title: "전표",
        items: [
          ["매출매입전표", "/invoice"],
          ["일반전표", "/journal"],
          ["자금전표", "/moneyInvoice"],
          ["급여전표", "/payment"],
          ["전표 마감", "/close"],
        ],
      },
      { title: "손익계산서", items: [["손익계산서", "/income"]] },
      { title: "재무상태표", items: [["재무상태표", "/balanceSheet"]] },
    ],
  },
  hr: {
    title: "인사",
    content: "인사 대시보드",
    groups: [
      {
        title: "사원관리",
        items: [
          ["사원 관리", "/emps"],
          ["수당 관리", "/allowcode"],
          ["공제 관리", "/deductcode"],
        ],
      },
    ],
  },
};

/** DOM 단축 */
const $ = (id) => document.getElementById(id);
const sidebarTitle = () => $("sidebarTitle");
const navRoot = () => $("navRoot");
const contentTitle = () => $("contentTitle");

/** 사이드바 렌더 */
function renderSidebar(key) {
  const data = MENUS[key] || MENUS.hr;
  const currentPath = window.location.pathname;
  const root = navRoot();
  if (!root) return;

  root.innerHTML = "";
  if (sidebarTitle()) sidebarTitle().textContent = data.title;
  if (contentTitle()) contentTitle().textContent = data.content;

  data.groups.forEach((grp) => {
    const wrap = document.createElement("div");
    wrap.className = "group";

    const gtitle = document.createElement("div");
    gtitle.className = "group-title";
    gtitle.textContent = grp.title;

    const ul = document.createElement("ul");
    ul.className = "sub";
    let hasActiveItem = false;

    grp.items.forEach(([label, href]) => {
      if (auth === "ROLE_USER") {
        const restricted = ["/accountList"];
        if (restricted.includes(href)) return;
      }

      const li = document.createElement("li");
      const a = document.createElement("a");
      a.href = href;
      a.textContent = label;

      const pathOnly = new URL(a.href, window.location.origin).pathname;
      if (pathOnly === currentPath) {
        a.classList.add("active");
        hasActiveItem = true;
      }

      // ✅ 링크 클릭 시 기본 동작 유지 + 토글 이벤트 차단
      a.addEventListener("click", (e) => {
        e.stopPropagation(); // 부모 div로 버블링 방지
      });

      li.appendChild(a);
      ul.appendChild(li);
    });

    // ✅ 그룹 토글은 group-title 클릭일 때만 작동
    gtitle.addEventListener("click", (e) => {
      if (e.target.closest("a")) return; // a 클릭은 무시
      ul.classList.toggle("open");
    });

    if (hasActiveItem) ul.classList.add("open");

    wrap.appendChild(gtitle);
    if (ul.children.length > 0) wrap.appendChild(ul);
    root.appendChild(wrap);
  });
}

/** 탭 이벤트 */
function initTabs() {
  const tabs = document.querySelectorAll(".tab");
  const currentPath = window.location.pathname;
  let activeTabKey = "hr";

  for (const key in MENUS) {
    for (const group of MENUS[key].groups) {
      if (group.items.some(([_, href]) => href === currentPath)) {
        activeTabKey = key;
        break;
      }
    }
    if (activeTabKey !== "hr") break;
  }

  tabs.forEach((t) => {
    const isActive = t.dataset.tab === activeTabKey;
    t.classList.toggle("active", isActive);
    t.setAttribute("aria-selected", isActive);

    t.addEventListener("click", (e) => {
      e.preventDefault();
      tabs.forEach((x) => {
        x.classList.remove("active");
        x.setAttribute("aria-selected", "false");
      });
      t.classList.add("active");
      t.setAttribute("aria-selected", "true");
      renderSidebar(t.dataset.tab);
    });
  });

  renderSidebar(activeTabKey);
}

/** 초기 실행 */
document.addEventListener("DOMContentLoaded", initTabs);
