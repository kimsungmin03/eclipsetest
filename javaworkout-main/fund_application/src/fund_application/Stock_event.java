package fund_application;

import java.util.*;

public class Stock_event {
		{
        List<String> companies = Arrays.asList("영카콜라", "영플", "테슬웅", "웅비디아", "하이비", "삼숭", "한하", "카카우");

        Map<String, List<Map<String, Object>>> companyNewsPool = new HashMap<>();
        companyNewsPool.put("영카콜라", Arrays.asList(
                createNews("미국 공장 화재로 100억 손실", -5),
                createNews("미식 축구 스폰서 계약", 3),
                createNews("공장 위생 문제로 주주총회 개최", -3)
        ));
        companyNewsPool.put("영플", Arrays.asList(
                createNews("USB-C 타입 도입 선언", -8),
                createNews("AI 기반 새 휴대폰 시리즈 개발", 3),
                createNews("사용자 휴대폰 폭발 사고", -10)
        ));
        companyNewsPool.put("테슬웅", Arrays.asList(
                createNews("자동차 날아다니는 기술 공개", 4),
                createNews("스페이스 Z, 화성 탐사선 출시", 3),
                createNews("샤인 머스크 폭행 사건", -6)
        ));
        companyNewsPool.put("웅비디아", Arrays.asList(
                createNews("KYW1011 그래픽카드 공개", 9),
                createNews("공장에서 이상 물질 검출", -12),
                createNews("경쟁사 직원 사망 사건 조사 중", -15)
        ));
        companyNewsPool.put("하이비", Arrays.asList(
                createNews("뉴티셔츠 데뷔, 인기 폭발", 20),
                createNews("계약 위반으로 6조 위약금", -18),
                createNews("대표 배임 의혹", -15)
        ));
        companyNewsPool.put("삼숭", Arrays.asList(
                createNews("AI로 사진 자동 보정 기능 공개", 8),
                createNews("휴대폰 신기술 발표", 3),
                createNews("대표 정치 개입설 논란", -5)
        ));
        companyNewsPool.put("한하", Arrays.asList(
                createNews("화약 제조로 10조 수익", 4),
                createNews("역 폭발사고로 5000명 중상", -15),
                createNews("가을야구 1등 전망", 2)
        ));
        companyNewsPool.put("카카우", Arrays.asList(
                createNews("워라밸 최고 기업 선정", 3),
                createNews("DB 서버 전소로 자료 소실", -17),
                createNews("대량 채용 선언", 4)
        ));

        Map<String, String> companyCategory = new HashMap<>();
        companyCategory.put("영카콜라", "음료수");
        companyCategory.put("영플", "IT");
        companyCategory.put("테슬웅", "IT");
        companyCategory.put("웅비디아", "IT");
        companyCategory.put("삼숭", "IT");
        companyCategory.put("하이비", "엔터");
        companyCategory.put("카카우", "엔터");
        companyCategory.put("한하", "중공업");

        Map<String, List<Map<String, Object>>> categoryGlobalNews = new HashMap<>();

        categoryGlobalNews.put("음료수", Arrays.asList(
                createNews("원자재 가격 상승으로 생산비 증가", -4),
                createNews("건강 음료 시장 급성장", 5)
        ));

        categoryGlobalNews.put("IT", Arrays.asList(
                createNews("신규 AI 칩 개발 성공", 7),
                createNews("글로벌 반도체 공급난 완화 기대", 6),
                createNews("해킹 사건으로 고객 데이터 유출", -8),

                createNews("전 세계 반도체 수요 폭증", 5),
                createNews("AI 혁신 기술 등장", 4),
                createNews("정부, 대기업 세금 감면 정책 발표", 6)
        ));

        categoryGlobalNews.put("엔터", Arrays.asList(
                createNews("신인 아이돌 그룹 데뷔 대성공", 10),
                createNews("콘서트 취소로 매출 감소", -7)
        ));

        categoryGlobalNews.put("중공업", Arrays.asList(
                createNews("해외 플랜트 수주 대박", 8),
                createNews("환경 규제 강화로 비용 상승", -6),

                createNews("세계 금융 위기 재현 우려", -7),
                createNews("국제 유가 급등", -4)
        ));

        Random rand = new Random();

        for (String company : companies) {
            System.out.println("-- " + company);

            // 고유 뉴스 10% 확률로 1개 출력
            if (rand.nextDouble() < 0.10) {
                List<Map<String, Object>> companyNews = companyNewsPool.getOrDefault(company, Collections.emptyList());
                if (!companyNews.isEmpty()) {
                    Map<String, Object> news = companyNews.get(rand.nextInt(companyNews.size()));
                    System.out.println(news.get("news") + " (주가 변동률: " + news.get("impact") + "%)");
                }
            } else {
                System.out.println("(고유 뉴스 없음)");
            }

            // 회사별로 각각 10% 확률로 범용 뉴스 노출
            if (rand.nextDouble() < 0.10) {
                String category = companyCategory.getOrDefault(company, "IT");
                List<Map<String, Object>> globalNewsForCategory = categoryGlobalNews.getOrDefault(category, Collections.emptyList());
                if (!globalNewsForCategory.isEmpty()) {
                    Map<String, Object> globalNews = globalNewsForCategory.get(rand.nextInt(globalNewsForCategory.size()));
                    System.out.println("[범용] " + globalNews.get("news") + " (주가 변동률: " + globalNews.get("impact") + "%)");
                }
            } else {
                System.out.println("[범용 뉴스 없음]");
            }

            System.out.println();
        }
    
	}
    public static Map<String, Object> createNews(String news, int impact) {
        Map<String, Object> item = new HashMap<>();
        item.put("news", news);
        item.put("impact", impact);
        return item;
    }
}
