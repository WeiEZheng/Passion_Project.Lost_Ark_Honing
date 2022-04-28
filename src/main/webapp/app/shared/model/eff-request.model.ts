export interface IEffRequest {
  id?: number;
  basePercent?: number | null;
  additionPercentPerFail?: number | null;
  maxPercentAfterMats?: number | null;
  fusionMat1Amount?: number | null;
  fusionMat2Amount?: number | null;
  fusionMat3Amount?: number | null;
  failLimit?: number | null;
  amountDiff?: number | null;
}

export const defaultValue: Readonly<IEffRequest> = {};
