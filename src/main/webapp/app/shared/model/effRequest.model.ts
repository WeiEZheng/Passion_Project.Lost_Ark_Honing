export interface effRequest {
  id?: number;
  basePercent?: number;
  additionPercentPerFail?: number;
  maxPercentAfterMats?: number;
  fusionMat1Amount?: number | null;
  fusionMat2Amount?: number | null;
  fusionMat3Amount?: number | null;
  failLimit?: number;
  eqid?: number;
}

export const defaultValue: Readonly<effRequest> = {};
