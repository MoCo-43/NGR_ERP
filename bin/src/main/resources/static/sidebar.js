/** 모듈별 메뉴 데이터 */
const MENUS = {
  hr: {
    title: "인사",
    content: "인사 대시보드",
    groups: [
      { title: "사원관리", items: [
        ["사원관리", "/hr/emp"],
        ["수당등록", "/hr/allow"],
        ["공제등록", "/hr/deduct"],
      ]},
      { title: "부서관리", items: [
        ["부서관리", "/hr/dept"],
      ]},
      { title: "급여관리", items: [
        ["급여대장", "/hr/payroll"],
        ["사원급여조회", "/hr/payview"],
        ["급여이체현황", "/hr/transfer"]
      ]},
    ]
  },

  inventory: {
    title: "재고",
    content: "재고 대시보드",
    groups: [
      { title: "입고관리", items: [
        ["입고조회","/inv/in/list"],
        ["입고등록","/inv/in/new"],
      ]},
      { title: "출고관리", items: [
        ["출고조회","/inv/out/list"],
        ["출고등록","/inv/out/new"],
      ]},
      { title: "발주관리", items: [
        ["발주계획조회","/inv/po/plan"],
        ["발주계획등록","/inv/po/plan/new"],
        ["발주서 조회","/inv/po/list"],
        ["발주서 등록","/inv/po/new"],
      ]},
      { title: "재고결산", items: [
        ["재고결산","/inv/closing"]
      ]},
      { title: "제품관리", items: [
        ["제품등록","/inv/product"]
      ]}
    ]
  },

  sales: {
    title: "영업",
    content: "영업 대시보드",
    groups: [
      { title: "주문서", items: [
        ["주문서조회","/sales/order/list"],
        ["주문서입력","/sales/order/new"]
      ]},
      { title: "출하지시서", items: [
        ["출하지시서조회","/sales/shipping/list"],
        ["출하지시서입력","/sales/shipping/new"]
      ]},
      { title: "거래처", items: [
        ["거래처관리","/sales/accounts"],
        ["여신관리","/sales/credit"]
      ]},
      { title: "영업관리현황", items: [
        ["거래명세서","/sales/statement"],
        ["주문서현황","/sales/order/status"],
        ["출하지시서현황","/sales/shipping/status"]
      ]}
    ]
  },

  accounting: {
    title: "회계",
    content: "회계 대시보드",
    groups: [
      { title: "계정 과목", items: [
        ["계정 과목 관리","/accountList"]
      ]},
      { title: "전표", items: [
        ["매출매입전표","/invoice"],
        ["일반전표","/acc/voucher/general"],
        ["자금전표","/acc/voucher/fund"],
        ["급여전표","/acc/voucher/payroll"],
        ["전표 마감","/acc/voucher/closing"]
      ]},
      { title: "손익계산서", items: [
        ["손익계산서","/acc/pl"]
      ]},
      { title: "재무상태표", items: [
        ["재무상태표","/acc/bs"]
      ]}
    ]
  }
};

/** DOM 참조 */
const $ = id => document.getElementById(id);
const sidebarTitle = () => $('sidebarTitle');
const navRoot = () => $('navRoot');
const contentTitle = () => $('contentTitle');

/** 사이드바 렌더 */
function renderSidebar(key){
  const data = MENUS[key] || MENUS.hr;

  if (sidebarTitle()) sidebarTitle().textContent = data.title;
  if (contentTitle()) contentTitle().textContent = data.content;

  const root = navRoot();
  if (!root) return;
  root.innerHTML = '';

  data.groups.forEach((grp)=>{
    const wrap = document.createElement('div');
    wrap.className = 'group';

    const gtitle = document.createElement('div');
    gtitle.className = 'group-title';
    gtitle.textContent = grp.title;

    const ul = document.createElement('ul');
    ul.className = 'sub';
    grp.items.forEach(([label, href])=>{
      const li = document.createElement('li');
      const a = document.createElement('a');
      a.href = href;
      a.textContent = label;
      li.appendChild(a);
      ul.appendChild(li);
    });

    gtitle.addEventListener('click', ()=>{
      ul.classList.toggle('open');
    });

    wrap.appendChild(gtitle);
    wrap.appendChild(ul);
    root.appendChild(wrap);
  });
}

/** 탭 이벤트 */
function initTabs(){
  const tabs = document.querySelectorAll('.tab');
  tabs.forEach(t=>{
    t.addEventListener('click', (e)=>{
      e.preventDefault();
      tabs.forEach(x=>{ x.classList.remove('active'); x.setAttribute('aria-selected','false'); });
      t.classList.add('active');
      t.setAttribute('aria-selected','true');
      renderSidebar(t.dataset.tab);
    });
  });
}

/** 초기 구동 */
document.addEventListener('DOMContentLoaded', ()=>{
  initTabs();
  renderSidebar('hr');
});
