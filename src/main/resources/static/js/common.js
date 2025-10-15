/* ===============================
   🌐 공통 로딩 유틸 함수
   (Grid / Modal / 전체화면 공용)
================================= */

/**
 * 로딩 오버레이 생성 (동적으로 추가)
 * @param {string} targetId - 로딩을 덮을 영역의 id (없으면 body 전체)
 * @param {string} message - 표시할 문구 (기본값: "데이터를 불러오는 중입니다...")
 */
function showLoader(targetId, message = "데이터를 불러오는 중입니다...") {
  let target = targetId ? document.getElementById(targetId) : document.body;
  if (!target) return;

  // 이미 존재한다면 중복 생성 방지
  if (target.querySelector(".erp-loader")) return;

  const loader = document.createElement("div");
  loader.className = "erp-loader";
  loader.innerHTML = `
    <div class="loader-content">
      <div class="spinner"></div>
      <p>${message}</p>
    </div>
  `;
  loader.style.position = target === document.body ? "fixed" : "absolute";
  target.appendChild(loader);
}

/**
 * 로딩 오버레이 제거
 * @param {string} targetId - 제거할 영역의 id (없으면 body 전체)
 */
function hideLoader(targetId) {
  let target = targetId ? document.getElementById(targetId) : document.body;
  if (!target) return;
  const loader = target.querySelector(".erp-loader");
  if (loader) {
    loader.classList.add("hidden");
    setTimeout(() => loader.remove(), 300);
  }
}
